package com.cleanup.todoc.dao;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Project.class, Task.class}, version = 1)
public abstract class TodocDatabase extends RoomDatabase{
    public abstract ProjectDao dao();

    public static volatile TodocDatabase INSTANCE;

    public static TodocDatabase getINSTANCE(Context c) {
        if(INSTANCE == null){
            synchronized (TodocDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(c.getApplicationContext(), TodocDatabase.class, "Todoc.db").addCallback(prepopulateDb()).build();
                }
            }
        }
        return INSTANCE;

    }
    private static Callback prepopulateDb(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Project[] projects = Project.getAllProjects();
                for (Project project:projects){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", project.getId());
                    contentValues.put("name", project.getName());
                    contentValues.put("color", project.getColor());
                    db.insert("projet", OnConflictStrategy.IGNORE, contentValues);
                }
            }
        };
    }
}
