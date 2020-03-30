package com.example.wikitea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wikitea.Tables.Favourite.Favorite;
import com.example.wikitea.Tables.Favourite.FavoriteViewModel;
import com.example.wikitea.Tables.Tea.Tea;
import com.example.wikitea.Tables.Tea.TeaAdapter;
import com.example.wikitea.Tables.Tea.TeaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TeaActivity extends AppCompatActivity {
    public static final int ADD_TEA_REQUEST = 1;
    public static final int EDIT_TEA_REQUEST = 2;

    private TeaViewModel teaViewModel;
    private FavoriteViewModel favoriteViewModel;
    private List<Tea> teas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //DARK/LIGHT THEME
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea);



        //toolbar
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Get the categoryId
        Bundle categoryIntent = getIntent().getExtras();
        final int categoryId = categoryIntent.getInt("EXTRA_CATEGORY_ID");

        FloatingActionButton buttonAddTea = (FloatingActionButton) findViewById(R.id.button_add_tea);

        buttonAddTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeaActivity.this, AddEditTeaActivity.class);
                intent.putExtra("EXTRA_CATEGORY_ID", categoryId);
                startActivityForResult(intent, ADD_TEA_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TeaAdapter adapter = new TeaAdapter();
        recyclerView.setAdapter(adapter);

        teas = new ArrayList<>();
        TeaViewModel.Factory factory = new TeaViewModel.Factory(getApplication(), categoryId);
        //teaViewModel = new TeaViewModel(this.getApplication(), categoryId);
        teaViewModel = ViewModelProviders.of(this, factory).get(TeaViewModel.class);
        teaViewModel.getAllTeasByCategory(categoryId).observe(this, (List<Tea> teaList) ->{
            if (teaList != null) {
                teas = teaList;
                adapter.setTeas(teas);
            }
        });

        //Swipe for delete a tea
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                teaViewModel.delete(adapter.getTeaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TeaActivity.this, "Tea deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);



        //Set long click action on a tea for add to favorites
        adapter.setOnItemLongClickListener(new TeaAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Tea tea) {

                Favorite favorite;

                String title = tea.getTitle();
                String description = tea.getDescription();
                String origin = tea.getOrigin();

                favorite = new Favorite (title, description, origin);

                favorite.setIdFavorite(tea.getIdTea());

                favoriteViewModel.insert(favorite);
            }
        });


        //For edit tea details
        adapter.setOnItemClickListener(new TeaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tea tea) {
                Intent intent = new Intent(TeaActivity.this, AddEditTeaActivity.class);
                intent.putExtra(AddEditTeaActivity.EXTRA_ID, tea.getIdTea());
                intent.putExtra(AddEditTeaActivity.EXTRA_TITLE, tea.getTitle());
                intent.putExtra(AddEditTeaActivity.EXTRA_DESCRIPTION, tea.getDescription());
                intent.putExtra(AddEditTeaActivity.EXTRA_ORIGIN, tea.getOrigin());
                intent.putExtra(AddEditTeaActivity.EXTRA_IDCATEGORYTEA, tea.getIdCategoryTea());
                startActivityForResult(intent, EDIT_TEA_REQUEST);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Get the categoryId
        Intent categoryIntent = getIntent();
        int categoryId = categoryIntent.getIntExtra("EXTRA_CATEGORY_ID", 0);


        //Save a new tea
        if (requestCode == ADD_TEA_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTeaActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTeaActivity.EXTRA_DESCRIPTION);
            String origin = data.getStringExtra(AddEditTeaActivity.EXTRA_ORIGIN);
            int categoryTeaId = data.getIntExtra(AddEditTeaActivity.EXTRA_IDCATEGORYTEA, 1);

            Tea tea = new Tea(title, description, origin, categoryTeaId);
            teaViewModel.insert(tea);
            Toast.makeText(this, "Tea saved", Toast.LENGTH_SHORT).show();

            //Edit a tea
        } else if (requestCode == EDIT_TEA_REQUEST && resultCode == RESULT_OK) { // EDIT A TEA
            int id = data.getIntExtra(AddEditTeaActivity.EXTRA_ID, -1);


            if (id == -1) {
                Toast.makeText(this, "Tea can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
        //receive information from AddEditTeaActivity
            String title = data.getStringExtra(AddEditTeaActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTeaActivity.EXTRA_DESCRIPTION);
            String origin = data.getStringExtra(AddEditTeaActivity.EXTRA_ORIGIN);
            int categoryTeaId = data.getIntExtra(AddEditTeaActivity.EXTRA_IDCATEGORYTEA, 1);


            Tea tea = new Tea(title, description, origin, categoryId);
            tea.setIdTea(id);
            //Update with the new tea
            teaViewModel.update(tea);

            Toast.makeText(this, "Tea updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tea not saved", Toast.LENGTH_SHORT).show();
        }
    }

    //Set the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.details_menu, menu);
        return true;
    }


//Toolbar options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(TeaActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_favorite:
                intent = new Intent(TeaActivity.this, FavoriteActivity.class);
                startActivity(intent);
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }


}

