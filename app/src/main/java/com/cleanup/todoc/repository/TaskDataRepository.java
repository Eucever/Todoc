package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {
    private final TaskDao mTaskDao;

    public TaskDataRepository (TaskDao taskDao) {mTaskDao = taskDao;}

    public LiveData<List<Task>> getAlltasks(){return mTaskDao.getAlltasks();}

    public void createTask(Task task){mTaskDao.insertTask(task);}

    public void deleteTask(Task task){mTaskDao.deleteTask(task);}

}
