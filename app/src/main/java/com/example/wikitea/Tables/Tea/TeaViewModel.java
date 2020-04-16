package com.example.wikitea.Tables.Tea;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import java.util.List;

public class TeaViewModel extends AndroidViewModel {
    private TeaRepository repository;
    private Application application;

    public TeaViewModel(@NonNull Application application, String id) {
        super(application);

        repository = new TeaRepository(application);

    }


    public void insert(Tea tea)
    {
        repository.insert(tea);
    }
    public void update(Tea tea)
    {
        repository.update(tea);
    }
    public void delete(Tea tea)
    {
        repository.delete(tea);
    }

    public void deleteAllTeas(){
        repository.deleteAllTeas();
    }

    public LiveData<List<Tea>> getAllTeasByCategory(String id) {
        return repository.getAllTeasById(id);
    };



    //Factory for custom viewmodel constructor
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String categoryId;


        public Factory(@NonNull Application application, String categoryId) {
            this.application = application;
            this.categoryId = categoryId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            //return (T) new TeaViewModel(application, categoryId, repository);
            return (T) new TeaViewModel(application, categoryId);
        }
    }

}


