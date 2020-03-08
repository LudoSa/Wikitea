package com.example.wikitea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

//Class for tea's categories
public class HomeActivity extends AppCompatActivity implements TeaListView.TeaListener {


    ListView list;
    String[] teaname = {"Green tea", "Black tea", "White tea", "Oolong tea", "Pu Er"};
    String[] teadescription = {"It's green", "It's black", "It's white", "It's not a color", "I don't now what is this"};
    Integer[] imgid={R.drawable.green, R.drawable.black, R.drawable.white, R.drawable.olong, R.drawable.puer};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        list = (ListView) findViewById(R.id.listview);
        TeaListView tealistview=new TeaListView(this, teaname, teadescription,imgid);
        list.setAdapter(tealistview);

    }

    @Override
    public void teaClick(int position) {


    }
}
