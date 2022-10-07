package com.wachiramartin.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.wachiramartin.todoapp.adapter.OnTaskClickListener;
import com.wachiramartin.todoapp.adapter.RecyclerViewAdapter;
import com.wachiramartin.todoapp.model.Task;
import com.wachiramartin.todoapp.viewmodel.SharedViewModel;
import com.wachiramartin.todoapp.viewmodel.TaskViewModel;


public class MainActivity extends AppCompatActivity implements OnTaskClickListener {
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private TaskViewModel viewModel;
    private CheckBox checkBox;
    private BottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkBox = findViewById(R.id.checkBox);

        bottomSheetFragment = new BottomSheetFragment();
        CoordinatorLayout coordinatorLayout = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<CoordinatorLayout> bottomSheetBehavior = BottomSheetBehavior.from(coordinatorLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN, true);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(TaskViewModel.class);
        viewModel.getAllTasks().observe(this, tasks -> {
            recyclerViewAdapter = new RecyclerViewAdapter(tasks, MainActivity.this, MainActivity.this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });
        //We instantiate it here because the BottomSheetFragment appears in the MainActivity
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> MainActivity.this.showBottomSheetDialogFragment());
        setRecyclerView();
    }

    private void showBottomSheetDialogFragment() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_todoApp:
               /*AboutFragment aboutFragment = new AboutFragment();
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container, aboutFragment)
                        .addToBackStack("About")
                        .commit();*/

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTaskClickListener(Task task) {
        sharedViewModel.selectedTask(task);
        sharedViewModel.setIsEdit(true);
        showBottomSheetDialogFragment();
    }

    @Override
    public void onCheckButtonClicked(int adapterPosition, Task task) {
        TaskViewModel.delete(task);
        Snackbar snackbar = Snackbar.make(recyclerView, R.string.snackBar_message, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.btn_undo, v -> TaskViewModel.insert(task)).show();
        recyclerViewAdapter.notifyItemRemoved(adapterPosition);
    }

}