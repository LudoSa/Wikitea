package com.example.wikitea.Tables.Tea;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wikitea.Tables.Category.Category;
import com.example.wikitea.Tables.Category.CategoryDao;

@Database(entities = {Tea.class}, version = 1, exportSchema = false)
public abstract class TeaDatabase extends RoomDatabase {

    private static TeaDatabase instance;

    public abstract TeaDao teaDao();

    public static synchronized com.example.wikitea.Tables.Tea.TeaDatabase getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), com.example.wikitea.Tables.Tea.TeaDatabase.class, "tea_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();

        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private TeaDao teaDao;

        private PopulateDbAsyncTask(TeaDatabase db){
            teaDao = db.teaDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            teaDao.insert(new Tea("tea vert","test", 10));
            return null;
        }
    }

}