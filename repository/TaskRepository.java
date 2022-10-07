package com.wachiramartin.todoapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.wachiramartin.todoapp.model.Task;
import com.wachiramartin.todoapp.model.TaskDao;
import com.wachiramartin.todoapp.model.TaskDataBase;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> tasks;

    public TaskRepository(Application application) {
        TaskDataBase taskDataBase = TaskDataBase.getInstance(application);
        taskDao = taskDataBase.taskDao();
        tasks = taskDao.tasks();
    }

    //Define all the query methods from taskDao

    public void insert(Task task) {
        TaskDataBase.executorService.execute(() -> taskDao.insert(task));
    }

    public void delete(Task task){
        TaskDataBase.executorService.execute(() -> taskDao.delete(task));
    }

    public void update(Task task){
        TaskDataBase.executorService.execute(() -> taskDao.update(task));
    }

    public void deleteAll(){
        TaskDataBase.executorService.execute(() -> taskDao.deleteAll());
    }

    public LiveData<List<Task>> getTasks(){
        return tasks;
    }

    public LiveData<Task> task(Long id){
        return taskDao.task(id);
    }

}
