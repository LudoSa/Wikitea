package com.example.wikitea.Tables.Favourite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wikitea.Tables.Tea.Tea;
import com.example.wikitea.Tables.Tea.TeaRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository repository;
    private LiveData<List<Favorite>> allFavorites;
    private Application application;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

        repository = new FavoriteRepository(application);

        allFavorites = repository.getAllFavorite();
    }


    public void insert(Favorite favorite)
    {
        repository.insert(favorite);
    }
    public void update(Favorite favorite)
    {
        repository.update(favorite);
    }
    public void delete(Favorite favorite)
    {
        repository.delete(favorite);
    }

    public void deleteAllFavorites(){
        repository.deleteAllFavorite();
    }
    public LiveData<List<Favorite>> getAllFavorites(){
        return allFavorites;
    }

}


