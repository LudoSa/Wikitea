package com.example.wikitea.Tables.Admin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wikitea.Tables.Admin.Admin;
import com.example.wikitea.Tables.Admin.AdminRepository;
import com.example.wikitea.Tables.Tea.TeaViewModel;

import java.util.List;

public class AdminViewModel extends AndroidViewModel {

    private AdminRepository repository;
    private LiveData<Admin> allAdmins;

    public AdminViewModel(@NonNull Application application, String name)
    {
        super(application);
        repository = new AdminRepository(application);
        allAdmins = repository.getAdminByName(name);

    }

    public LiveData<Admin> getAdminByName(String name)
    {
        return repository.getAdminByName(name);
    }


    public void insert(Admin admin){
        repository.insert(admin);
    }
    public void update(Admin admin){
        repository.update(admin);
    }
    public void delete(Admin admin){
        repository.delete(admin);
    }




    //Factory for custom viewmodel constructor
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String name;


        public Factory(@NonNull Application application, String name) {
            this.application = application;
            this.name = name;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            //return (T) new TeaViewModel(application, categoryId, repository);
            return (T) new AdminViewModel(application, name);
        }
    }



}