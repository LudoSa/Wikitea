package com.example.wikitea.Tables.Tea;

import android.icu.util.ULocale;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.wikitea.Tables.Category.Category;


@Entity(tableName = "tea_table", foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "idCategory",
        childColumns = "idCategory",
        onDelete = ForeignKey.CASCADE))
public class Tea {

    @PrimaryKey(autoGenerate = true)
    private int idTea;

    private String title;

    private String description;

    private String origin ;

    @ColumnInfo(name = "idCategory")
    private int idCategoryTea;



    public Tea(String title, String description, String origin, int idCategoryTea)
    {
        this.title = title;
        this.description = description;
        this.origin = origin;
        this.idCategoryTea = idCategoryTea;
    }

    public void setIdCategoryTea(int idCategoryTea) { this.idCategoryTea = idCategoryTea; }

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

    public String getOrigin() {
        return origin;
    }

    public int getIdCategoryTea() {
        return idCategoryTea;
    }

}
