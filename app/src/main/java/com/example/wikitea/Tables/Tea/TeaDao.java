package com.example.wikitea.Tables.Tea;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TeaDao {

    @Insert
    void insert(Tea tea);

    @Update
    void update(Tea tea);

    @Delete
    void delete(Tea tea);
    //IL SERA POSSIBLE DE SUPPRIMER DES LISTES ;) = DELETE ALL TEA FROM CATEGORY


    @Query("DELETE FROM tea_table")
    void deleteAllTeas();

    @Query("SELECT * FROM tea_table ORDER BY id DESC")
    LiveData<List<Tea>> getAllTeas();


}

