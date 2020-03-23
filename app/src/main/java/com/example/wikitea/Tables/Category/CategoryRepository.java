package com.example.wikitea.Tables.Category;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryRepository {

    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;

    public CategoryRepository(Application application){

        CategoryDatabase database = CategoryDatabase.getInstance(application);
        categoryDao = database.categoryDao();
        allCategories = categoryDao.getAllCategories();
    }

    public void insert(Category category){

        new InsertCategoryAsyncTask(categoryDao).execute(category);

    }

    public void update(Category category){

        new UpdateCategoryAsyncTask(categoryDao).execute(category);

    }

    public void delete(Category category){

        new DeleteCategoryAsyncTask(categoryDao).execute(category);

    }

    public void deleteAllCategories(){

        new DeleteAllCategoryAsyncTask(categoryDao).execute();

    }

    public LiveData<List<Category>> getAllCategories(){

        return allCategories;
    }

    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void>{

        private CategoryDao categoryDao;

        private InsertCategoryAsyncTask(CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {

            categoryDao.insert(categories[0]);

            return null;

        }
    }


    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void>{

        private CategoryDao categoryDao;

        private UpdateCategoryAsyncTask(CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {

            categoryDao.update(categories[0]);

            return null;

        }
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void>{

        private CategoryDao categoryDao;

        private DeleteCategoryAsyncTask(CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {

            categoryDao.delete(categories[0]);

            return null;

        }
    }

    private static class DeleteAllCategoryAsyncTask extends AsyncTask<Void, Void, Void>{

        private CategoryDao categoryDao;

        private DeleteAllCategoryAsyncTask(CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            categoryDao.deleteAllCategories();

            return null;

        }
    }

}
