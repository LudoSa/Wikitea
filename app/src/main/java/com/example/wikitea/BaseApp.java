package com.example.wikitea;

import android.app.Application;

import com.example.wikitea.Tables.Admin.AdminDatabase;
import com.example.wikitea.Tables.Admin.AdminRepository;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public AdminDatabase getDatabase() {
        return AdminDatabase.getInstance(this);
    }

    public AdminRepository getAdminRepository() {
        return AdminRepository.getInstance();
    }

}
