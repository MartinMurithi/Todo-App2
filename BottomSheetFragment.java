package com.wachiramartin.todoapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.wachiramartin.todoapp.model.Task;
import com.wachiramartin.todoapp.util.HideSoftKeyBoard;
import com.wachiramartin.todoapp.viewmodel.SharedViewModel;
import com.wachiramartin.todoapp.viewmodel.TaskViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    public static EditText et_taskTitle;
    public static EditText et_taskDetails;
    private ImageButton imageButton_taskDetails;
    private ImageButton imageButton_date;
    private Button save;
    private CalendarView calendarView;
    private Button setTimeButton;
    private SharedViewModel sharedViewModel;
    private boolean isEdit;
    public static Date dueDate;
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
        et_taskTitle = view.findViewById(R.id.editText_taskTitle);
        et_taskDetails = view.findViewById(R.id.editText_details);
        imageButton_taskDetails = view.findViewById(R.id.imageButton_taskDetails);
        imageButton_date = view.findViewById(R.id.imageButton_date);
        save = view.findViewById(R.id.button_save);
        calendarView = view.findViewById(R.id.calendarView);
        setTimeButton = view.findViewById(R.id.btn_setTime);

        imageButton_taskDetails.setOnClickListener(v -> {
            et_taskDetails.requestFocus();
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getSelectedTask().getValue() != null) {
            isEdit = sharedViewModel.getIsEdit();
            Task task = sharedViewModel.getSelectedTask().getValue();
            et_taskTitle.setText(task.getTaskTitle());
            et_taskDetails.setText(task.getTaskDetails());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        calendarView.setVisibility(View.GONE);
        imageButton_date.setOnClickListener(v -> {
            calendarView.setVisibility(calendarView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            HideSoftKeyBoard.hideKeyBoard(calendarView);
        });

        calendarView.setOnDateChangeListener((CalendarView, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dueDate = calendar.getTime();
            HideSoftKeyBoard.hideKeyBoard(calendarView);
        });

        save.setOnClickListener(v -> {
            String taskTitle = et_taskTitle.getText().toString().trim();
            String taskDetails = et_taskDetails.getText().toString().trim();

            if (!TextUtils.isEmpty(taskTitle) && dueDate != null) {
                Task task = new Task(taskTitle, taskDetails, dueDate, false);

                if (isEdit) {
                    Task updateTask = sharedViewModel.getSelectedTask().getValue();
                    updateTask.setTaskTitle(taskTitle);
                    updateTask.setTaskDetails(taskDetails);
                    updateTask.setDateCreated(dueDate);
                    TaskViewModel.update(updateTask);
                    sharedViewModel.setIsEdit(false);
                } else {
                    TaskViewModel.insert(task);
                }
                et_taskTitle.setText("");
                et_taskDetails.setText("");

                if (this.isVisible()) {
                    this.dismiss();
                }
            } else {
                Snackbar.make(v, "Please enter Task Title and date", Snackbar.LENGTH_SHORT).show();
            }

        });
    }
}
