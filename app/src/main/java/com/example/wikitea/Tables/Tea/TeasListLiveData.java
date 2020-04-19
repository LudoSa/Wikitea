package com.example.wikitea.Tables.Tea;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.wikitea.Tables.Category.Category;
import com.example.wikitea.Tables.Category.CategoryListLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeasListLiveData extends LiveData<List<Tea>> {


    private static final String TAG = "TeaListLiveData";

    private final DatabaseReference reference;
    private final String id;
    private final MyValueEventListener listener = new MyValueEventListener();


    public TeasListLiveData(DatabaseReference ref, String id){

        reference = ref;
        this.id = id;

    }



    @Override
    protected void onActive () {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive () {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toTeas(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Tea> toTeas (DataSnapshot snapshot){
        List<Tea> teas = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Tea entity = childSnapshot.getValue(Tea.class);
            entity.setIdTea(childSnapshot.getKey());
            entity.setIdCategoryTea(id);
            teas.add(entity);
        }
        return teas;
    }

}
