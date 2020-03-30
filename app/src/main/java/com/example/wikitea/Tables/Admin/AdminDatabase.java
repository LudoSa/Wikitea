package com.example.wikitea.Tables.Admin;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wikitea.Tables.Admin.Admin;
import com.example.wikitea.Tables.Admin.Admin;

@Database(entities = {Admin.class}, version = 1, exportSchema = false)
public abstract class AdminDatabase extends RoomDatabase {

    private static AdminDatabase instance;
    //public abstract AdminDao admindao();

    public static synchronized AdminDatabase getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AdminDatabase.class, "admin_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;

    }

    private static Callback roomCallback = new Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public abstract AdminDao adminDao();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private AdminDao adminDao;

        private PopulateDbAsyncTask(AdminDatabase db){
            adminDao = db.adminDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            adminDao.insert(new Admin("Ludovic", "ludovic"));
            adminDao.insert(new Admin("Alexandre", "alexandre"));
            return null;
        }
    }

    //protected abstract AdminDao admindao();

}
