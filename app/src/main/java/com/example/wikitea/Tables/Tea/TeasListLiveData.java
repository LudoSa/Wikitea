package com.example.wikitea.Tables.Tea;

import androidx.lifecycle.LiveData;
import androidx.room.Database;

import com.example.wikitea.Tables.Tea.Tea;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class TeasListLiveData extends LiveData<List<Tea>> {


    private final DatabaseReference reference;
    private final String id;

    public TeasListLiveData(DatabaseReference ref, String id){

        reference = ref;
        this.id = id;

    }

}
