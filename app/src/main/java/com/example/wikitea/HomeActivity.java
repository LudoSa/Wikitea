package com.example.wikitea;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

//Class for tea's categories
public class HomeActivity extends AppCompatActivity implements TeaListView.TeaListener {

    ListView listView;
    String[] typeTea = {"Green tea", "Black tea", "White tea", "Oolong tea", "Pu Er"};
    String[] teadescription = {"It's green", "It's black", "It's white", "It's not a color", "I don't know what is this"};
    Integer[] imgid={R.drawable.green, R.drawable.black, R.drawable.white, R.drawable.olong, R.drawable.puer};


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listView = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, typeTea);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String TempString = typeTea[position].toString();

                Intent intent = new Intent(HomeActivity.this, TeaTypeActivity.class);

                intent.putExtra("teaTypeClicked", TempString);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {




        switch (item.getItemId()) {
            case R.id.action_settings:

                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new SettingsFrag())
                        .commit();

            case R.id.action_search:

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }

}
