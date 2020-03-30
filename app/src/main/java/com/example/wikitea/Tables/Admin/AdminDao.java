package com.example.wikitea.Tables.Admin;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wikitea.Tables.Admin.Admin;

import java.util.List;

@Dao
public interface AdminDao
{
        @Query("SELECT * FROM admin_table WHERE name = :id")
        LiveData<Admin> getById(String id);

        @Query("SELECT * FROM admin_table")
        LiveData<List<Admin>> getAllAdmins();

        @Insert
        void insert(Admin admin);

        @Update
        void update(Admin admin);

        @Delete
        void delete(Admin admin);


}
