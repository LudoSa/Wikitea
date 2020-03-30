package com.example.wikitea.Tables.Tea;

import android.icu.util.ULocale;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.wikitea.Tables.Category.Category;


@Entity(tableName = "tea_table")
public class Tea {

    @PrimaryKey(autoGenerate = true)
    private int idTea;

    private String title;

    private String description;

    private int price ;



    public Tea(String title, String description, int price)
    {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public void setIdTea(int idTea) {
        this.idTea = idTea;
    }


    //GETTERS
    public int getIdTea() {
        return idTea;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
