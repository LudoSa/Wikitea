package com.example.wikitea.Tables.Favourite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wikitea.Tables.Tea.Tea;
import com.example.wikitea.Tables.Tea.TeaDao;
import com.example.wikitea.Tables.Tea.TeaDatabase;

import java.util.List;

public class FavoriteRepository {
    private FavoriteDao favoriteDao;
    private LiveData<List<Favorite>> allFavorite;
    private static FavoriteRepository instance;



public FavoriteRepository(Application application)
{
    TeaDatabase database = TeaDatabase.getInstance(application);
    favoriteDao = database.favoriteDao();
    allFavorite = favoriteDao.getAllFavourites();
}



    //REPOSITERY:
        //ALL OF THESE METHODS ARE API REPOSITORY EXPOSED TO THE OUTSIDE.
    public void insert(Favorite favorite)
    {
        new InsertTeaAsyncTask(favoriteDao).execute(favorite);
    }

public void update(Favorite favorite)
{
    new UpdateTeaAsyncTask(favoriteDao).execute(favorite);
}

public void delete(Favorite favorite)
{
new DeleteTeaAsyncTask(favoriteDao).execute(favorite);
}

public void deleteAllFavorite()
{
new DeleteAllFavoritesAsyncTask(favoriteDao).execute();
}



public LiveData<List<Favorite>> getAllFavorite()
{
    return allFavorite;
}


private static class InsertTeaAsyncTask extends AsyncTask<Favorite, Void, Void>
{
    private FavoriteDao favoriteDao;

    private InsertTeaAsyncTask(FavoriteDao favoriteDao)
    {
        this.favoriteDao = favoriteDao;
    }


    @Override
    protected Void doInBackground(Favorite... favorites) {
        favoriteDao.insert(favorites[0]);  //Begins with the first tea
        return null;
    }
}

    private static class UpdateTeaAsyncTask extends AsyncTask<Favorite, Void, Void>
    {
        private FavoriteDao favoriteDao;

        private UpdateTeaAsyncTask(FavoriteDao favoriteDao)
        {
            this.favoriteDao = favoriteDao;
        }


        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.update(favorites[0]);  //Begins with the first tea
            return null;
        }
    }


    private static class DeleteTeaAsyncTask extends AsyncTask<Favorite, Void, Void>
    {
        private FavoriteDao favoriteDao;

        private DeleteTeaAsyncTask(FavoriteDao favoriteDao)
        {
            this.favoriteDao = favoriteDao;
        }


        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.delete(favorites[0]);  //Begins with the first tea
            return null;
        }
    }

    private static class DeleteAllFavoritesAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private FavoriteDao favoriteDao;

        private DeleteAllFavoritesAsyncTask(FavoriteDao favoriteDao)
        {
            this.favoriteDao = favoriteDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            favoriteDao.deleteAllFavourites();
            return null;
        }
    }



}
