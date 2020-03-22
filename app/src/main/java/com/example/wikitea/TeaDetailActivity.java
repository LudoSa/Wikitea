package com.example.wikitea;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class TeaDetailActivity extends AppCompatActivity {

    TextView textView;

    ImageButton mapButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Set the textView with the string from Intent (clicked)
        textView=(TextView)findViewById(R.id.teaClicked);
        String TempTea=getIntent().getStringExtra("teaClicked");
        textView.setText(TempTea);


        //Create the button in order to open the fragment "maps"
        mapButton = findViewById(R.id.mapButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                //When clicked, the fragment is created
                TestFragment tf = new TestFragment();

                fm.beginTransaction().replace(R.id.detailLayout,tf).addToBackStack(null).commit();

            }
        });








        //Map_fragment map_fragment = new Map_fragment();
        //FragmentManager fm = getSupportFragmentManager();

        //fm.beginTransaction().add(R.id.m)


    }

}
