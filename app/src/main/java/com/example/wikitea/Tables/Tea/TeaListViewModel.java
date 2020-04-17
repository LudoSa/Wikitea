package com.example.wikitea.Tables.Tea;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wikitea.BaseApp;
import com.example.wikitea.Tables.Category.Category;
import com.example.wikitea.Tables.Category.CategoryListViewModel;
import com.example.wikitea.Tables.Category.CategoryRepository;
import com.example.wikitea.util.OnAsyncEventListener;

import java.util.List;

public class TeaListViewModel extends AndroidViewModel {

    private TeaRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.

    //private final MediatorLiveData<List<TeasFromCategory>> mObservableCategoryTeas;
    private final MediatorLiveData<List<Tea>> mObservableTeas;

    public TeaListViewModel(@NonNull Application application, String categoryId,
                                 TeaRepository teaRepository) {
        super(application);

        mRepository = teaRepository;

        mObservableTeas = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableTeas.setValue(null);

        LiveData<List<Tea>> tea = mRepository.getAllTeasById(categoryId);

        // observe the changes of the category entity from the database and forward them
        mObservableTeas.addSource(tea, mObservableTeas::setValue);

    }

    /**
     * A creator is used to inject the category id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final TeaRepository mRepository;

        private final String mCategoryId;

        public Factory(@NonNull Application application, String categoryId) {
            mApplication = application;
            mRepository = ((BaseApp) application).getTeaRepository();
            mCategoryId = categoryId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TeaListViewModel(mApplication, mCategoryId, mRepository);
        }
    }

    /**
     * Expose the LiveData Category query so the UI can observe it.
     */
    public LiveData<List<Tea>> getTeasById() {
        return mObservableTeas;
    }

    public void createTea(Tea tea, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getTeaRepository()
                .insert(tea, callback);
    }

    public void updateTea(Tea tea, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getTeaRepository()
                .update(tea, callback);
    }


}
