package com.example.wikitea.Tables.Admin;

import android.app.Application;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminRepository {
    
    private FirebaseAuth fAuth;

    //Constructor
    public AdminRepository(Application application){

        fAuth = FirebaseAuth.getInstance();
    }

    public void signIn(String email, String password, OnCompleteListener<AuthResult> listener){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }



}