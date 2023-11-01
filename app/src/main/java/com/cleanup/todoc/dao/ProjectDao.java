package com.cleanup.todoc.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

@androidx.room.Dao
public interface ProjectDao {
    // contient les instruction pour la base de donnée concernant la table projet
    @Query("SELECT * FROM projet")
    LiveData<List<Project>> getAllProjects();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProjects(Project... projects);

}
