package com.example.wikitea;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikitea.Tables.Category.Category;
import com.example.wikitea.Tables.Category.CategoryAdapter;
import com.example.wikitea.Tables.Category.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//Class for tea's categories
public class CategoryActivity extends AppCompatActivity implements TeaListView.TeaListener {

    public static final int ADD_CATEGORY_REQUEST = 1;


    private CategoryViewModel categoryViewModel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        FloatingActionButton buttonAddCategory = findViewById(R.id.button_add_category);
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CategoryActivity.this, AddCategoryActivity.class);
                startActivityForResult(intent, ADD_CATEGORY_REQUEST);

            }
        });




        //List of categories
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CategoryAdapter adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {

            @Override
            public void onChanged(List<Category> categories) {
                //Create entities in the list
                adapter.setCategories(categories);
            }
        });


        //toolbar
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == ADD_CATEGORY_REQUEST && resultCode == RESULT_OK){

            String name = data.getStringExtra(AddCategoryActivity.EXTRA_NAME);
            String virtue = data.getStringExtra(AddCategoryActivity.EXTRA_VIRTUES);

            Category category = new Category(name, virtue);
            categoryViewModel.insert(category);

            Toast.makeText(this, "category saved", Toast.LENGTH_LONG).show();

        } else{

            Toast.makeText(this, "category not saved", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_settings:

                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new SettingsFrag()).addToBackStack(null)
                        .commit();

            case R.id.action_search:

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }

}
