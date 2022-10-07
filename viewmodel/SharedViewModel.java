package com.wachiramartin.todoapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wachiramartin.todoapp.model.Task;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<Task> selectedTask = new MutableLiveData<>();
    private boolean isEdit;

    public LiveData<Task> getSelectedTask() {
        return selectedTask;
    }

    public void selectedTask(Task task) {
        selectedTask.setValue(task);
    }

    public boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit){
        this.isEdit = isEdit;
    }
}
