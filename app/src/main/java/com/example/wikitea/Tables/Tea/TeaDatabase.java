package com.example.wikitea.Tables.Tea;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Tea.class}, version = 1, exportSchema = false)
public abstract class TeaDatabase extends RoomDatabase {

    private static TeaDatabase instance;
    public abstract TeaDao teaDao();

    //Only one thread at a time can be executed
   public static synchronized TeaDatabase getInstance(Context context){
       if(instance == null) {

           instance = Room.databaseBuilder(context.getApplicationContext(),
                   TeaDatabase.class,"tea_database")
                   .fallbackToDestructiveMigration()
                   .addCallback(roomCallBack)
                   .build();

       }
       return instance;


   }

   private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback()
   {
       @Override
       public void onCreate(@NonNull SupportSQLiteDatabase db) {
           super.onCreate(db);
           new PopulateDbAsyncTask(instance).execute();
       }
   };

   private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>
   {
      private TeaDao teaDao;

      private PopulateDbAsyncTask(TeaDatabase db)
      {
          teaDao = db.teaDao();
      }

       @Override
       protected Void doInBackground(Void... voids) {
          teaDao.insert(new Tea("First Tea !","Good tea",69));
           teaDao.insert(new Tea("Second Tea !","Excellent tea",70));
           teaDao.insert(new Tea("Third Tea !","Bad tea",666));

           return null;
       }
   }
}
