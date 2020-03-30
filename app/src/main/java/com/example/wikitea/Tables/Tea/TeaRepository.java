package com.example.wikitea.Tables.Tea;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;


import java.util.List;

public class TeaRepository {
    private TeaDao teaDao;
    private LiveData<List<Tea>> allTeas;
    private LiveData<List<Tea>> allTeasById;
    private int id;
    private static TeaRepository instance;



public TeaRepository(Application application)
{
    TeaDatabase database = TeaDatabase.getInstance(application);
    teaDao = database.teaDao();
    allTeas = teaDao.getAllTeas();
    //allTeasById = teaDao.getAllTeasByCategory(id);
}



    //REPOSITERY:
        //ALL OF THESE METHODS ARE API REPOSITORY EXPOSED TO THE OUTSIDE.
    public void insert(Tea tea)
    {
        new InsertTeaAsyncTask(teaDao).execute(tea);
    }

public void update(Tea tea)
{
    new UpdateTeaAsyncTask(teaDao).execute(tea);
}

public void delete(Tea tea)
{
new DeleteTeaAsyncTask(teaDao).execute(tea);
}

public void deleteAllTeas()
{
new DeleteAllTeasAsyncTask(teaDao).execute();
}



public LiveData<List<Tea>> getAllTeas()
{
    return allTeas;
}


private static class InsertTeaAsyncTask extends AsyncTask<Tea, Void, Void>
{
    private TeaDao teaDao;

    private InsertTeaAsyncTask(TeaDao teaDao)
    {
        this.teaDao = teaDao;
    }


    @Override
    protected Void doInBackground(Tea... teas) {
        teaDao.insert(teas[0]);  //Begins with the first tea
        return null;
    }
}

    private static class UpdateTeaAsyncTask extends AsyncTask<Tea, Void, Void>
    {
        private TeaDao teaDao;

        private UpdateTeaAsyncTask(TeaDao teaDao)
        {
            this.teaDao = teaDao;
        }


        @Override
        protected Void doInBackground(Tea... teas) {
            teaDao.update(teas[0]);  //Begins with the first tea
            return null;
        }
    }


    private static class DeleteTeaAsyncTask extends AsyncTask<Tea, Void, Void>
    {
        private TeaDao teaDao;

        private DeleteTeaAsyncTask(TeaDao teaDao)
        {
            this.teaDao = teaDao;
        }


        @Override
        protected Void doInBackground(Tea... teas) {
            teaDao.delete(teas[0]);  //Begins with the first tea
            return null;
        }
    }

    private static class DeleteAllTeasAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private TeaDao teaDao;

        private DeleteAllTeasAsyncTask(TeaDao teaDao)
        {
            this.teaDao = teaDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            teaDao.deleteAllTeas();
            return null;
        }
    }



}
