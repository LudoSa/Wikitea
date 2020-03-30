package com.example.wikitea.Tables.Admin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wikitea.Tables.Admin.Admin;
import com.example.wikitea.Tables.Admin.AdminRepository;

import java.util.List;

public class AdminViewModel extends AndroidViewModel {

    private AdminRepository repository;
    private LiveData<List<Admin>> allAdmins;

    public AdminViewModel(@NonNull Application application) {
        super(application);
        repository = new AdminRepository(application);
        allAdmins = repository.getAllAdmins();

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



    public LiveData<List<Admin>> getAllAdmins(){
        return allAdmins;
    }


}