package com.wachiramartin.todoapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "task_title")
    private String taskTitle;

    @ColumnInfo(name = "task_details")
    private String taskDetails;

    @ColumnInfo(name = "date_created")
    private Date dateCreated;

    @ColumnInfo(name = "is_task_complete")
    private boolean isComplete;

    public Task(String taskTitle, String taskDetails, Date dateCreated, boolean isComplete) {
        this.taskTitle = taskTitle;
        this.taskDetails = taskDetails;
        this.dateCreated = dateCreated;
        this.isComplete = isComplete;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
