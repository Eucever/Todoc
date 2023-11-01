package com.cleanup.todoc.injection;

import android.content.Context;

import com.cleanup.todoc.dao.TodocDatabase;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    //Crée un nouvelle executor
    private static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    //Permet de récupérer les repository de projet et taches
    private static ProjectDataRepository provideProjectDataRepo(Context context){
        TodocDatabase database = TodocDatabase.getINSTANCE(context);
        return new ProjectDataRepository(database.projectDao());

    }
    private static TaskDataRepository provideTaskDataRepo(Context context){
        TodocDatabase database = TodocDatabase.getINSTANCE(context);
        return new TaskDataRepository(database.taskDao());
    }

    //Récupere la factory du viewmodel

    public static ViewModelFactory provideViewModelFactory(Context context){
        ProjectDataRepository projectDataRepository = provideProjectDataRepo(context);
        TaskDataRepository taskDataRepository = provideTaskDataRepo(context);
        Executor executor = provideExecutor();

        return new ViewModelFactory(projectDataRepository, taskDataRepository, executor);
    }


}
