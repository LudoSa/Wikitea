package com.example.wikitea;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

//Class for tea's categories
public class HomeActivity extends AppCompatActivity implements TeaListView.TeaListener {


    ListView list;
    String[] teaname = {"Green tea", "Black tea", "White tea", "Oolong tea", "Pu Er"};
    String[] teadescription = {"It's green", "It's black", "It's white", "It's not a color", "I don't now what is this"};
    Integer[] imgid={R.drawable.green, R.drawable.black, R.drawable.white, R.drawable.olong, R.drawable.puer};


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setActionBar(toolbar);




        list = (ListView) findViewById(R.id.listview);
        TeaListView tealistview=new TeaListView(this, teaname, teadescription,imgid);
        list.setAdapter(tealistview);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position == 1) {
                    Intent tealist = new Intent(view.getContext(), TeaTypeActivity.class);
                    startActivity(tealist);
                }
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(this, settings.class);
                startActivity(intent);


            case R.id.action_search:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
