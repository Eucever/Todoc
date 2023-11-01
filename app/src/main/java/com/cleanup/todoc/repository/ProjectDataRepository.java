package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao mProjectDao;

    // Permet d'initier le repository
    public ProjectDataRepository (ProjectDao projectDao) {mProjectDao = projectDao;}

    public LiveData<List<Project>> getAllProjects(){ return mProjectDao.getAllProjects();}

    public void insertProjects(Project... projects){mProjectDao.insertProjects(projects);}

}
