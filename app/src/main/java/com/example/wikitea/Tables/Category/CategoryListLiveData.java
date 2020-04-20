package com.example.wikitea.Tables.Category;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CategoryListLiveData extends LiveData<List<Category>> {

    private static final String TAG = "CategoryListLiveData";

    private final DatabaseReference reference;
    public String id;
    private final MyValueEventListener listener = new MyValueEventListener();


    public CategoryListLiveData(DatabaseReference reference, String id) {

        this.reference = reference;
        this.id = id ;
    }

    public CategoryListLiveData(DatabaseReference reference)
    {
        this.reference = reference;
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
                setValue(toCategories(dataSnapshot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
            }
        }

        private List<Category> toCategories (DataSnapshot snapshot){
            List<Category> categories = new ArrayList<>();
            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                Category entity = childSnapshot.getValue(Category.class);
                entity.setIdCategory(childSnapshot.getKey());
                //entity.setName(name);
                categories.add(entity);
            }
            return categories;
        }


}
