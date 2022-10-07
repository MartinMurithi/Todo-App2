package com.wachiramartin.todoapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wachiramartin.todoapp.model.Task;
import com.wachiramartin.todoapp.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private static TaskRepository taskRepository;
    private LiveData<List<Task>> tasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        tasks = taskRepository.getTasks();
    }

    public static void insert(Task task){
        taskRepository.insert(task);
    }

    public static void update(Task task){
        taskRepository.update(task);
    }

    public static void delete(Task task){
        taskRepository.delete(task);
    }

    public static void deleteAll(){
        taskRepository.deleteAll();
    }

    public LiveData<Task> getTask(Long id){
       return taskRepository.task(id);
    }

    public LiveData<List<Task>> getAllTasks(){
        return  tasks;
    }

}
