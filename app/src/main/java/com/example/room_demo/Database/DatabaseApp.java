package com.example.room_demo.Database;



import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.room_demo.Model.Employee;


@androidx.room.Database(entities = {Employee.class}, version = 1, exportSchema = false)
public abstract class DatabaseApp extends RoomDatabase {
    private static final String LOG_TAG = DatabaseApp.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "personlist";
    private static DatabaseApp sInstance;

    public static DatabaseApp getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                       DatabaseApp.class, DatabaseApp.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract EmpolyeeDao empolyeeDao();
}
