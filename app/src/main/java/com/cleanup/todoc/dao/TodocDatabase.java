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
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    public static volatile TodocDatabase INSTANCE;

    //fonction qui permet de récuperer une instance de la base
    public static TodocDatabase getINSTANCE(Context c) {
        if(INSTANCE == null){
            synchronized (TodocDatabase.class){
                if (INSTANCE == null){
                    // si l'instance n'existe pas on crée la base de donnée
                    INSTANCE = Room
                            .databaseBuilder(c.getApplicationContext(), TodocDatabase.class, "Todoc.db")
                            .addCallback(prepopulateDb())
                            .build();
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
                //On récupère la liste de projets de la classe Project
                Project[] projects = Project.getAllProjects();
                for (Project project:projects){
                    //Pour chaque projet on assigne les valeurs id,name et color
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", project.getId());
                    contentValues.put("name", project.getName());
                    contentValues.put("color", project.getColor());
                    //On insert chaque projet dans la base
                    db.insert("projet", OnConflictStrategy.IGNORE, contentValues);
                }
            }
        };
    }
}
