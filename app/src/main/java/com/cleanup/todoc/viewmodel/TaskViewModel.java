package com.cleanup.todoc.viewmodel;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;


public class TaskViewModel {

    private final ProjectDataRepository mProjectRepo;

    private final TaskDataRepository mTaskRepo;

    private final Executor mExecutor;


    public TaskViewModel (ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor){
        mProjectRepo = projectDataRepository;
        mTaskRepo = taskDataRepository;
        mExecutor = executor;
    }

    //For Project

    public LiveData<List<Project>> getAllProjects(){
        return mProjectRepo.getAllProjects();
    }

    public void insertProjects(Project... projects){
        mProjectRepo.insertProjects(projects);
    }

    // For Task
    public LiveData<List<Task>> getAlltasks(){
       return mTaskRepo.getAlltasks();
    }
    public void createTask (long id, long projectId, String name,long creationTimestamp ){
        mExecutor.execute(()->{
            mTaskRepo.createTask(new Task(id, projectId, name, creationTimestamp));
        });
    }
    public void deleteTask(Task task){
        mExecutor.execute(()->{
            mTaskRepo.deleteTask(task);
        });
    }




}
