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

    private LiveData<List<Project>> mProjects;




    public TaskViewModel (ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor){
        mProjectRepo = projectDataRepository;
        mTaskRepo = taskDataRepository;
        mExecutor = executor;
    }


    //For Project

    public void init(){
        if(mProjects != null){
            return;
        }
        mProjects = mProjectRepo.getAllProjects();

    }
    public LiveData<List<Project>> getAllProjects(){
        return mProjects;
    }

    public void insertProjects(Project... projects){
        mProjectRepo.insertProjects(projects);
    }

    // For Task
    public LiveData<List<Task>> getAllTasks(){
       return mTaskRepo.getAllTasks();
    }
    public void createTask (Task task){
        mExecutor.execute(()->{
            mTaskRepo.createTask(task);
        });
    }
    public void deleteTask(Task task){
        mExecutor.execute(()->{
            mTaskRepo.deleteTask(task);
        });
    }




}
