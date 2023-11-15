package com.cleanup.todoc;


import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;

import com.cleanup.todoc.dao.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class TaskDaoTest {
    @Rule
    // Rule to execute tasks synchronously
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    // Get reference to the ToDoc database
    private TodocDatabase database;


    //List of all projects to insert in database
    private final Project[] mProjects = Project.getAllProjects();


    // List of tasks to insert
    private final Task task1 = new Task( 1, "Tache 1", 123);
    private final Task task2 = new Task( 2, "Tache 2", 124);
    private final Task task3 = new Task( 3, "Tache 3", 125);

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
    public void insertAndGetTask() throws InterruptedException {
        List<Task> taskList = LiveDataTestUtil.getOrAwaitValue(this.database.taskDao().getAlltasks());

        assertTrue(taskList.isEmpty());

        this.database.projectDao().insertProjects(mProjects);

        this.database.taskDao().insertTask(task1);
        this.database.taskDao().insertTask(task2);
        this.database.taskDao().insertTask(task3);

        taskList = LiveDataTestUtil.getOrAwaitValue(this.database.taskDao().getAlltasks());
        task1.setId(1);
        task2.setId(2);
        task3.setId(3);

        assertTrue(taskList.size() == 3);
        assertTrue(taskList.get(0).getName().equals(task1.getName()));
        assertTrue(taskList.get(0).getProjectId()== task1.getProjectId());
        assertTrue(taskList.get(1).getName().equals(task2.getName()));
        assertTrue(taskList.get(1).getId() == task2.getId());
        assertTrue(taskList.get(2).getName().equals(task3.getName()));

    }

    @Test
    public void deleteAndGetTask() throws InterruptedException {
        this.database.projectDao().insertProjects(mProjects);

        this.database.taskDao().insertTask(task1);
        this.database.taskDao().insertTask(task2);
        this.database.taskDao().insertTask(task3);

        List<Task> taskList = LiveDataTestUtil.getOrAwaitValue(this.database.taskDao().getAlltasks());

        assertTrue(taskList.size() == 3);

        this.database.taskDao().deleteTask(taskList.get(0));

        taskList = LiveDataTestUtil.getOrAwaitValue(this.database.taskDao().getAlltasks());

        assertTrue(taskList.size()==2);
        assertTrue(taskList.get(0).getName().equals((task2.getName())));
        assertTrue(taskList.get(1).getName().equals((task3.getName())));

    }

}
