package com.example.wikitea;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TeaDetailActivity extends AppCompatActivity {

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView=(TextView)findViewById(R.id.teaClicked);

        String TempTea=getIntent().getStringExtra("teaClicked");

        textView.setText(TempTea);

    }

}
