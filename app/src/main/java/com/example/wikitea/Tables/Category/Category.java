package com.example.wikitea.Tables.Category;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "category_table")
public class Category {

    @PrimaryKey(autoGenerate = true)

    private int id;

    private String name;

    private String virtues;

    public Category(String name, String virtues){

        this.name = name;
        this.virtues = virtues;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getVirtues(){
        return virtues;
    }



}
