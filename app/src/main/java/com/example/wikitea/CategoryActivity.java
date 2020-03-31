package com.example.wikitea;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikitea.Tables.Category.Category;
import com.example.wikitea.Tables.Category.CategoryAdapter;
import com.example.wikitea.Tables.Category.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//Class for tea's categories
public class CategoryActivity extends AppCompatActivity {

    public static final int ADD_CATEGORY_REQUEST = 1;
    public static final int EDIT_CATEGORY_REQUEST = 2;

    private CategoryViewModel categoryViewModel;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //DARK/LIGHT THEME
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);



        //toolbar
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //Button to add a new category
        FloatingActionButton buttonAddCategory = findViewById(R.id.button_add_category);
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CategoryActivity.this, AddEditCategoryActivity.class);
                startActivityForResult(intent, ADD_CATEGORY_REQUEST);

            }
        });

        //Set the recycler for the generate the list
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CategoryAdapter adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);

        //Set the list
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {

            @Override
            public void onChanged(List<Category> categories) {
                //Create entities in the list
                adapter.setCategories(categories);
            }
        });

        //Swipe delete item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                categoryViewModel.delete(adapter.getCategoryAt(viewHolder.getAdapterPosition()));
                Toast.makeText(CategoryActivity.this, "Category deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);


        //Set long click action on an item in the recyclerview
        adapter.setOnItemLongClickListener(new CategoryAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Category category) {
                Intent intent = new Intent(CategoryActivity.this, AddEditCategoryActivity.class);
                intent.putExtra(AddEditCategoryActivity.EXTRA_IDCATEGORY, category.getIdCategory());
                intent.putExtra(AddEditCategoryActivity.EXTRA_NAME, category.getName());
                intent.putExtra(AddEditCategoryActivity.EXTRA_VIRTUES, category.getVirtues());
                startActivityForResult(intent, EDIT_CATEGORY_REQUEST);
            }
        });


        //Simple click, we take the id to get the list of teas
        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category)
            {
                Intent intent = new Intent(CategoryActivity.this, TeaActivity.class);
                intent.putExtra("EXTRA_CATEGORY_ID", category.getIdCategory());
                startActivity(intent);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == ADD_CATEGORY_REQUEST && resultCode == RESULT_OK){

            String name = data.getStringExtra(AddEditCategoryActivity.EXTRA_NAME);
            String virtue = data.getStringExtra(AddEditCategoryActivity.EXTRA_VIRTUES);

            Category category = new Category(name, virtue);
            categoryViewModel.insert(category);

            Toast.makeText(this, "category saved", Toast.LENGTH_LONG).show();

        } else  if (requestCode == EDIT_CATEGORY_REQUEST && resultCode == RESULT_OK){

            int id = data.getIntExtra(AddEditCategoryActivity.EXTRA_IDCATEGORY, -1);

            if (id == -1){
                Toast.makeText(this, "Category can't be updated", Toast.LENGTH_LONG).show();
                return;
            }

            String name = data.getStringExtra(AddEditCategoryActivity.EXTRA_NAME);
            String virtue = data.getStringExtra(AddEditCategoryActivity.EXTRA_VIRTUES);

            Category category = new Category(name, virtue);
            category.setIdCategory(id);
            categoryViewModel.update(category);

            Toast.makeText(this, "Category updated", Toast.LENGTH_LONG).show();

        }else {
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

        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(CategoryActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;


            case R.id.action_delete_all_categories:
                categoryViewModel.deleteAllCategories();
                Toast.makeText(this, "All categories deleted", Toast.LENGTH_LONG).show();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }


}
