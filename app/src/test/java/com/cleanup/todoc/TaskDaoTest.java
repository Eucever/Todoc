package com.cleanup.todoc;

import com.cleanup.todoc.dao.TodocDatabase;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TaskDaoTest {
    private TodocDatabase database;


    @Rule
     public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


}
