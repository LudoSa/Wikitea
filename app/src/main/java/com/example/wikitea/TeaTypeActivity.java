package com.example.wikitea;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

public class TeaTypeActivity extends AppCompatActivity {

    ListView listView;
    String[] teaname = {"Congou","Lapsang souchong","Keemun","Assam","Munnar","Nepali","Ceylon","Rize","Lahijan"};
    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_type);

        //Received the type of tea
        textView=(TextView)findViewById(R.id.teaTypeClicked);
        String TempTea=getIntent().getStringExtra("teaTypeClicked");
        textView.setText(TempTea);


        listView = (ListView) findViewById(R.id.tealist);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1,teaname);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        list = (ListView) findViewById(R.id.tealist);

        listView.setAdapter(arrayAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String TempString = teaname[position].toString();

                Intent intent = new Intent(TeaTypeActivity.this, TeaDetailActivity.class);

                intent.putExtra("teaClicked", TempString);
                startActivity(intent);


                    Intent tealist = new Intent(view.getContext(), TeaDetailActivity.class);
                    startActivity(tealist);

            }
        });


    }

}
