package com.example.wikitea;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.wikitea.R;
import com.example.wikitea.Tables.Tea;
import com.example.wikitea.Tables.TeaViewModel;

import java.util.List;

//Class to manage the listview of the homeActivity
public class TeaListView extends AppCompatActivity {

    //Declare the viewModel we'll use
    private TeaViewModel teaViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        teaViewModel = ViewModelProviders.of(this).get(TeaViewModel.class);
        teaViewModel.getAllTeas().observe(this, new Observer<List<Tea>>() {
            @Override
            public void onChanged(@Nullable List<Tea> teas) {

                //update RecyclerView
                Toast.makeText(TeaListView.this,"onChanged",Toast.LENGTH_SHORT).show();
            }
        });


    }



}
