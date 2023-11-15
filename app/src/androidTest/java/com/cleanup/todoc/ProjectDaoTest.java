package com.cleanup.todoc;

import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;

import com.cleanup.todoc.dao.TodocDatabase;
import com.cleanup.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class ProjectDaoTest {

    @Rule
    // Rule to execute tasks synchronously
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    // Get reference to the ToDoc database
    private TodocDatabase database;
    // Get the list of static initial projects from the model Project class
    private final Project[] mProjects = Project.getAllProjects();

    @Before
    public void initDatabase() {
        // Create an in-memory blank version of the database
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                        TodocDatabase.class)
                // Allow main thread queries, just for testing
                .allowMainThreadQueries()
                // Build the database
                .build();
    }
    @After
    public void closeDatabase() {
        // Close the database after the tests have been run
        this.database.close();
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        this.database.projectDao().insertProjects(mProjects);
       List<Project> projects = LiveDataTestUtil.getOrAwaitValue(this.database.projectDao().getAllProjects());

       assertTrue(projects.get(0).getId() == mProjects[0].getId());
       assertTrue(projects.get(1).getId() == mProjects[1].getId());
       assertTrue(projects.get(2).getId() == mProjects[2].getId());


    }
    // https://pastebin.com/nxstNBY5

}
