package com.example.wikitea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TeaTypeActivity extends AppCompatActivity {

    ListView list;
    String[] teaname = {"tea0", "tea1", "tea2", "tea3", "tea4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_type);

        list = (ListView) findViewById(R.id.tealist);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teaname);

        list.setAdapter(arrayAdapter);




    }

}
