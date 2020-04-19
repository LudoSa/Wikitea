package com.example.wikitea;

import android.app.Application;

import com.example.wikitea.Tables.Category.CategoryRepository;
import com.example.wikitea.Tables.Tea.TeaRepository;

public class BaseApp extends Application {

    public CategoryRepository getCategoryRepository() {
        return CategoryRepository.getInstance();
    }

    public TeaRepository getTeaRepository() {
        return TeaRepository.getInstance();
    }
    /*
    public ClientRepository getClientRepository() {
        return ClientRepository.getInstance();
    }
     */
}

