package com.cleanup.todoc.injection;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;
import com.cleanup.todoc.viewmodel.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory{

    private final ProjectDataRepository mProjectRepo;

    private final TaskDataRepository mTaskRepo;

    private final Executor mExecutor;

    //Met en place la factory grace au repositories et a l'executor
    public ViewModelFactory(ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor) {

        mProjectRepo = projectDataRepository;
        mTaskRepo =taskDataRepository;
        mExecutor = executor;
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(mProjectRepo, mTaskRepo, mExecutor);
        }

        // If the ViewModel class is unknown, throw an exception
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
