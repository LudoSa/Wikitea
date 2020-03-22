package com.example.wikitea.Tables;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TeaViewModel extends AndroidViewModel {
    private TeaRepository repository;
    private LiveData<List<Tea>> allTeas;

    public TeaViewModel(@NonNull Application application) {
        super(application);
        repository = new TeaRepository(application);
        allTeas = repository.getAllTeas();
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

    public LiveData<List<Tea>> getAllTeas()
    {
        return allTeas;
    }
}
