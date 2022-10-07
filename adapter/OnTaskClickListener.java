package com.wachiramartin.todoapp.adapter;

import com.wachiramartin.todoapp.model.Task;

public interface OnTaskClickListener {
    void onTaskClickListener(Task task);
    void onCheckButtonClicked(int adapterPosition, Task task);

}
