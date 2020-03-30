package com.example.wikitea.Tables.Admin;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "admin_table")
public class Admin {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String password;

    public Admin(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }


    //Getters
    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
