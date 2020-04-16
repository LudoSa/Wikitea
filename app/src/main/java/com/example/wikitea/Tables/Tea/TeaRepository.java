package com.example.wikitea.Tables.Tea;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;


import com.example.wikitea.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TeaRepository {
    private LiveData<List<Tea>> allTeasById;
    private int id;



public TeaRepository(Application application)
{

}

    public LiveData<List<Tea>> getAllTeasById(String id)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("categories").child(id).child("teas");
        return new TeasListLiveData(reference, id);
    }

    public void insert(final Tea tea, final OnAsyncEventListener callback) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(tea.getIdTea())
                .child("teas");

        String key = reference.push().getKey();

        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(tea.getIdTea())
                .child("accounts")
                .child(key)
                .setValue(tea, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Tea tea, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(tea.getIdCategoryTea())
                .child("teas")
                .child(tea.getIdTea())
                .updateChildren(tea.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


    public void delete(final Tea tea, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(tea.getIdCategoryTea())
                .child("teas")
                .child(tea.getIdTea())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
