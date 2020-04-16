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

public class CategoryViewModel extends AndroidViewModel
{
    private CategoryRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Category> mObservableCategory;

    public CategoryViewModel(@NonNull Application application,
                            final String idCategory, CategoryRepository categoryRepository) {
        super(application);

        mRepository = categoryRepository;

        mObservableCategory = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableCategory.setValue(null);

        if (idCategory != null) {
            LiveData<Category> category = mRepository.getCategory(idCategory);

            // observe the changes of the category entity from the database and forward them
            mObservableCategory.addSource(category, mObservableCategory::setValue);
        }
    }

    /**
     * A creator is used to inject the category id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final String mCategoryId;

        private final CategoryRepository mRepository;

        public Factory(@NonNull Application application, String idCategory) {
            mApplication = application;
            mCategoryId = idCategory;
            mRepository = ((BaseApp) application).getCategoryRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CategoryViewModel(mApplication, mCategoryId, mRepository);
        }
    }

    /**
     * Expose the LiveData Category query so the UI can observe it.
     */
    public LiveData<Category> getCategory() {
        return mObservableCategory;
    }

    public void createCategory(Category category, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getCategoryRepository()
                .insert(category, callback);
    }

    public void updateCategory(Category category, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getCategoryRepository()
                .update(category, callback);
    }
}