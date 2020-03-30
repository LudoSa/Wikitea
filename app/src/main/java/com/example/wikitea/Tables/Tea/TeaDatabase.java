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
import com.example.wikitea.Tables.Favourite.Favorite;
import com.example.wikitea.Tables.Favourite.FavoriteDao;

@Database(entities = {Tea.class, Category.class, Favorite.class}, version = 2, exportSchema = false)
public abstract class TeaDatabase extends RoomDatabase {

    private static TeaDatabase instance;

    public abstract TeaDao teaDao();
    public abstract CategoryDao categoryDao();
    public abstract FavoriteDao favoriteDao();
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
        private CategoryDao categoryDao;
        private  FavoriteDao favoriteDao;

        private PopulateDbAsyncTask(TeaDatabase db){
            teaDao = db.teaDao();
            categoryDao = db.categoryDao();
            favoriteDao = db.favoriteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            categoryDao.insert(new Category("Category 1", "Good"));
            categoryDao.insert(new Category("Category 2", "Good"));
            categoryDao.insert(new Category("Category 3", "Good"));
            categoryDao.insert(new Category("Category 4", "Good"));


            teaDao.insert(new Tea("First Tea !","Good tea","China", 1));
            teaDao.insert(new Tea("Second Tea !","Excellent tea","China",1));
            teaDao.insert(new Tea("Third Tea !","Bad tea","Europe", 2));
            teaDao.insert(new Tea("Fourth Tea !","Disgusting tea","Europe", 2));
            teaDao.insert(new Tea("Fifth Tea !","Very good tea","USA", 3));
            teaDao.insert(new Tea("Sixth Tea !","Good tea","USA", 4));
            return null;

        }
    }

}