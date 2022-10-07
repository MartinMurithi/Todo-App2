package com.wachiramartin.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.wachiramartin.todoapp.R;
import com.wachiramartin.todoapp.util.Util;
import com.wachiramartin.todoapp.model.Task;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private static List<Task> tasks;
    private final Context context;
    private final OnTaskClickListener onTaskClickListener;

    public RecyclerViewAdapter(List<Task> tasks, Context context, OnTaskClickListener onTaskClickListener) {
        this.tasks = tasks;
        this.context = context;
        this.onTaskClickListener = onTaskClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_task_irow, parent, false);
        return new MyViewHolder(view, onTaskClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        Task task = tasks.get(position);
        String date = Util.stringDate(task.getDateCreated());

        holder.textView_taskTitle.setText(task.getTaskTitle());
        holder.textView_taskDescription.setText(task.getTaskDetails());
        holder.chip_dateCreated.setText(date);
        holder.checkBox.setChecked(task.isComplete());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textView_taskTitle;
        private final TextView textView_taskDescription;
        private final Chip chip_dateCreated;
        private final CheckBox checkBox;
        OnTaskClickListener onTaskClickListener;

        public MyViewHolder(@NonNull View itemView, OnTaskClickListener onTaskClickListener) {
            super(itemView);
            textView_taskTitle = itemView.findViewById(R.id.textView_taskTitle);
            textView_taskDescription = itemView.findViewById(R.id.textView_taskDetails);
            chip_dateCreated = itemView.findViewById(R.id.chip_taskDueDate);
            checkBox = itemView.findViewById(R.id.checkBox);
            this.onTaskClickListener = onTaskClickListener;

            itemView.setOnClickListener(this);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            Task currTask = tasks.get(getAdapterPosition());

            if(id == R.id.cardView_task_row_item){
                onTaskClickListener.onTaskClickListener(currTask);
            }else if(id == R.id.checkBox){
                onTaskClickListener.onCheckButtonClicked(getAdapterPosition(), currTask);
            }
        }
    }
}
