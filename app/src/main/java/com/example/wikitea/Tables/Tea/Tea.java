package com.example.wikitea.Tables.Tea;

import android.icu.util.ULocale;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


//@Entity(tableName = "tea_table",foreignKeys = @ForeignKey(entity = ULocale.Category.class,
  //                                              parentColumns = "id",
    //                                            childColumns = "categoryId",
      //                                          onDelete = CASCADE))
@Entity (tableName = "tea_table")
public class Tea {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private int price ;

   // private int categoryId;


    public Tea(String title, String description, int price)
    {
        this.title = title;
        this.description = description;
        this.price = price;
        //this.categoryId = categoryId;
    }

    //Only need this setter because we didn't put it in the constructor
    public void setId(int id) {
        this.id = id;
    }


    //GETTERS
    public int getId() {
        return id;
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

    //public int getCategoryId() {
       // return categoryId;
   // }
}
