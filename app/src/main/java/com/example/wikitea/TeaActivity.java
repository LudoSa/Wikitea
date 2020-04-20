package com.example.wikitea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wikitea.Tables.Tea.Tea;
import com.example.wikitea.Tables.Tea.TeaAdapter;
import com.example.wikitea.Tables.Tea.TeaListViewModel;
import com.example.wikitea.util.OnAsyncEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//Class for tea's activity
public class TeaActivity extends AppCompatActivity {
    public static final int ADD_TEA_REQUEST = 1;
    public static final int EDIT_TEA_REQUEST = 2;

    private static final String TAG = "TeaActivity";

    private TeaListViewModel viewModel;
    private List<Tea> teas;
    private String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //DARK/LIGHT THEME
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea);



        //toolbar
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Get the categoryId
        Bundle categoryIntent = getIntent().getExtras();
        categoryId = categoryIntent.getString("EXTRA_CATEGORY_ID");

        FloatingActionButton buttonAddTea = (FloatingActionButton) findViewById(R.id.button_add_tea);

        buttonAddTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeaActivity.this, AddEditTeaActivity.class);
                intent.putExtra("EXTRA_CATEGORY_ID", categoryId);
                startActivityForResult(intent, ADD_TEA_REQUEST);
            }
        });

        //Set the recycler for the generate the list
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TeaAdapter adapter = new TeaAdapter();
        recyclerView.setAdapter(adapter);

        TeaListViewModel.Factory factory = new TeaListViewModel.Factory(getApplication(), categoryId);

        //Set the list

        viewModel = ViewModelProviders.of(this, factory).get(TeaListViewModel.class);
        viewModel.getTeasById().observe(this, teas_firebases ->{
            if (teas_firebases != null) {
                teas = (List<Tea>) teas_firebases;
                adapter.setTeas(teas);
            }
        });



        //Swipe right to left to delete a category
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteTea(adapter.getTeaAt(viewHolder.getAdapterPosition()), new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "deleteTea: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "deleteTea: failure");
                    }
                });
                Toast.makeText(TeaActivity.this, "Tea deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        //For edit tea details
        adapter.setOnItemClickListener(new TeaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tea tea) {
                Intent intent = new Intent(TeaActivity.this, AddEditTeaActivity.class);
                intent.putExtra(AddEditTeaActivity.EXTRA_ID, tea.getIdTea());
                intent.putExtra(AddEditTeaActivity.EXTRA_TITLE, tea.getTitle());
                intent.putExtra(AddEditTeaActivity.EXTRA_DESCRIPTION, tea.getDescription());
                intent.putExtra(AddEditTeaActivity.EXTRA_ORIGIN, tea.getOrigin());
                intent.putExtra("EXTRA_CATEGORY_ID", tea.getIdCategoryTea());
                startActivityForResult(intent, EDIT_TEA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Get the categoryId
        Intent categoryIntent = getIntent();
        String categoryId = categoryIntent.getStringExtra("EXTRA_CATEGORY_ID");


        //Get the values from AddEditTeaActivity
        if (requestCode == ADD_TEA_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTeaActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTeaActivity.EXTRA_DESCRIPTION);
            String origin = data.getStringExtra(AddEditTeaActivity.EXTRA_ORIGIN);
            String categoryTeaId = data.getStringExtra(AddEditTeaActivity.EXTRA_IDCATEGORYTEA);

            //Create a new tea
            Tea tea = new Tea(title, description, origin, categoryTeaId);
            viewModel.createTea(tea, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "createTea: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "createTea: failure", e);
                }
            });
            Toast.makeText(this, "Tea saved", Toast.LENGTH_SHORT).show();

            //Edit a tea
        } else if (requestCode == EDIT_TEA_REQUEST && resultCode == RESULT_OK) {

            //Get the id from the tea we selected
            String id = data.getStringExtra(AddEditTeaActivity.EXTRA_ID);

            //receive information from AddEditTeaActivity
            String title = data.getStringExtra(AddEditTeaActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTeaActivity.EXTRA_DESCRIPTION);
            String origin = data.getStringExtra(AddEditTeaActivity.EXTRA_ORIGIN);
            //String categoryTeaId = data.getStringExtra(AddEditTeaActivity.EXTRA_IDCATEGORYTEA);

            Tea tea = new Tea(title, description, origin, categoryId);
            tea.setIdTea(id);
            tea.setTitle(title);

            //Update with the new tea
            viewModel.updateTea(tea, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "updateTea: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "updateTea: failure", e);
                }
            });

            Toast.makeText(this, "Tea updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tea not saved", Toast.LENGTH_SHORT).show();
        }
    }

    //Set the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tea_menu, menu);
        return true;
    }


    //Toolbar options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:

                //OPEN FRAGMENT SETTING
                getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).addToBackStack(null).commit();
                return true;

            case R.id.action_delete_all_teas:
                viewModel.deleteAllTeas(categoryId, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "deleteAllTeas: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "deleteAllTeas: failure");
                    }
                });
                Toast.makeText(this, "All teas deleted", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_logout:
                startActivity(new Intent(TeaActivity.this,LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

