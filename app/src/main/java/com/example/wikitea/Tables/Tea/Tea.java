package com.example.wikitea.Tables.Tea;

import android.icu.util.ULocale;

import com.example.wikitea.Tables.Category.Category;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class Tea {

    @Exclude
    private String idTea;

    private String title;

    private String description;

    private String origin ;

    private String idCategoryTea;


    public Tea(){

    }

    public Tea(String title, String description, String origin, String idCategoryTea)
    {
        this.title = title;
        this.description = description;
        this.origin = origin;
        this.idCategoryTea = idCategoryTea;
    }


    public void setIdCategoryTea(String idCategoryTea) { this.idCategoryTea = idCategoryTea; }

    public void setIdTea(String idTea) {
        this.idTea = idTea;
    }

    public void setTitle(String title)  {this.title = title; }

    //GETTERS
    public String getIdTea() {
        return idTea;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOrigin() {
        return origin;
    }

    public String getIdCategoryTea() {
        return idCategoryTea;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("description", description);
        result.put("origin", origin);
        result.put("idCategoryTea", idCategoryTea);

        return result;
    }

}
