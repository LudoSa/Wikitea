package com.example.wikitea;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wikitea.Tables.Favourite.Favorite;
import com.example.wikitea.Tables.Favourite.FavoriteAdapter;
import com.example.wikitea.Tables.Favourite.FavoriteViewModel;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private FavoriteViewModel favoriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        //toolbar
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.recycler_view_favourite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final FavoriteAdapter adapter = new FavoriteAdapter();
        recyclerView.setAdapter(adapter);

        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.getAllFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                adapter.setFavorites(favorites);
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
                favoriteViewModel.delete(adapter.getFavouriteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(FavoriteActivity.this, "Favorite deleted", Toast.LENGTH_LONG).show();
            }

        }).attachToRecyclerView(recyclerView);

    }

    //Set the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favorites_menu, menu);
        return true;
    }


    //Toolbar options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new SettingsFrag()).addToBackStack(null)
                        .commit();


            case R.id.action_delete_all_favorites:
                favoriteViewModel.deleteAllFavorites();
                Toast.makeText(this, "All favorites deleted", Toast.LENGTH_LONG).show();
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }

}
