package com.wachiramartin.todoapp.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 2, exportSchema = false)

@TypeConverters({Converters.class})
public abstract class TaskDataBase extends RoomDatabase {
    private static TaskDataBase INSTANCE;
    private static final int numberOfThreads = 5;
   // public static final String database_name = "task_database";
    public static final ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

    private static final Migration migrate1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE task_database ADD COLUMN hour INTEGER");
            database.execSQL("ALTER TABLE task_database ADD COLUMN minute INTEGER");
        }
    };

    public static TaskDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDataBase.class) {
                INSTANCE = Room.databaseBuilder(context, TaskDataBase.class, "task_database")
                        .addCallback(roomCallback)
                        .addMigrations(migrate1_2)
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            TaskDao taskDao = INSTANCE.taskDao();
            executorService.execute(() -> taskDao.deleteAll());
        }
    };

}
