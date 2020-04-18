package com.example.wikitea.Tables.Category;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.wikitea.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import java.util.List;

public class CategoryRepository
{
    private static CategoryRepository instance;


    public static CategoryRepository getInstance() {
        if (instance == null)
        {
            synchronized (CategoryRepository.class)
            {
                if (instance == null)
                {
                    instance = new CategoryRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<Category>> getByName(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(name)
                .child("teas");
        return new CategoryListLiveData(reference, name);
    }

    public LiveData<Category> getCategory(final String idCategory) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("accounts")
                .child(idCategory);
        return new CategoryLiveData(reference);
    }

    public LiveData<List<Category>> getAllCategories() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("categories");
        return new CategoryListLiveData(reference);
    }



    public void insert(final Category category, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(category.getName());

        String key = reference.push().getKey();

        FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(category.getName())
                .setValue(category, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Category category, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(category.getName())
                .child("teas")
                .child(category.getIdCategory())
                .updateChildren(category.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Category category, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("categories")
                .child(category.getName())
                .child("teas")
                .child(category.getIdCategory())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void deleteAllCategories(){

        //new DeleteAllCategoryAsyncTask(categoryDao).execute();

    }
}
