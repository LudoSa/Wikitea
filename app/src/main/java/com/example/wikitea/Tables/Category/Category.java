package com.example.wikitea.Tables.Category;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Category {

    private int idCategory;

    private String name;

    private String virtues;

    public Category(String name, String virtues){

        this.name = name;
        this.virtues = virtues;

    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdCategory(){
        return idCategory;
    }

    public String getName(){
        return name;
    }

    public String getVirtues(){
        return virtues;
    }



}
