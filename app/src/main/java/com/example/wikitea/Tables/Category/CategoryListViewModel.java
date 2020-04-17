package com.example.wikitea.Tables.Category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wikitea.BaseApp;
import com.example.wikitea.util.OnAsyncEventListener;

import java.util.List;

public class CategoryListViewModel extends AndroidViewModel
{
    private CategoryRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.

    //private final MediatorLiveData<List<TeasFromCategory>> mObservableCategoryTeas;
    private final MediatorLiveData<List<Category>> mObservableCategories;

    public CategoryListViewModel(@NonNull Application application,
                                 CategoryRepository categoryRepository) {
        super(application);

        mRepository = categoryRepository;

        mObservableCategories = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableCategories.setValue(null);

            LiveData<List<Category>> category = mRepository.getAllCategories();

            // observe the changes of the category entity from the database and forward them
            mObservableCategories.addSource(category, mObservableCategories::setValue);

    }

    /**
     * A creator is used to inject the category id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final CategoryRepository mRepository;

        public Factory(@NonNull Application application) {
            mApplication = application;
            mRepository = ((BaseApp) application).getCategoryRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CategoryListViewModel(mApplication, mRepository);
        }
    }

    /**
     * Expose the LiveData Category query so the UI can observe it.
     */
    public LiveData<List<Category>> getCategories() {
        return mObservableCategories;
    }

    public void createCategory(Category category, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getCategoryRepository()
                .insert(category, callback);
    }

    public void updateCategory(Category category, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getCategoryRepository()
                .update(category, callback);
    }

    public void deleteCategory(Category category, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getCategoryRepository()
                .delete(category, callback);
    }
}