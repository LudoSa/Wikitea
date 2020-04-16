package com.example.wikitea.Tables.Category;


import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class Category implements Comparable{
    private String idCategory;
    private String name;
    private String virtues;


    public Category() {
    }

    public Category(@NonNull String name, String virtues)
    {
        this.name = name;
        this.virtues = virtues;
    }

    @Exclude
    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVirtues() {
        return virtues;
    }

    public void setVirtues(String virtues) {
        this.virtues = virtues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Category)) return false;
        Category o = (Category) obj;
        return o.getIdCategory().equals(this.getIdCategory());
    }


    //Useless ?
    @Override
    public String toString() {
        return name + " " + virtues;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("virtues", virtues);

        return result;
    }



}
