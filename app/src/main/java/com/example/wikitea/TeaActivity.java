package com.example.wikitea;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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

import com.example.wikitea.Tables.Tea.Tea;
import com.example.wikitea.Tables.Tea.TeaAdapter;
import com.example.wikitea.Tables.Tea.TeaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TeaActivity extends AppCompatActivity {
    public static final int ADD_TEA_REQUEST = 1;
    public static final int EDIT_TEA_REQUEST = 2;

    private TeaViewModel teaViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddTea = findViewById(R.id.button_add_tea);
        buttonAddTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeaActivity.this, AddEditTeaActivity.class);
                startActivityForResult(intent, ADD_TEA_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TeaAdapter adapter = new TeaAdapter();
        recyclerView.setAdapter(adapter);

        teaViewModel = ViewModelProviders.of(this).get(TeaViewModel.class);
        teaViewModel.getAllTeas().observe(this, new Observer<List<Tea>>() {
            @Override
            public void onChanged(@Nullable List<Tea> teas) {
                adapter.setTeas(teas);
            }
        });

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

        adapter.setOnItemClickListener(new TeaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tea tea) {
                Intent intent = new Intent(TeaActivity.this, AddEditTeaActivity.class);
                intent.putExtra(AddEditTeaActivity.EXTRA_ID, tea.getId());
                intent.putExtra(AddEditTeaActivity.EXTRA_TITLE, tea.getTitle());
                intent.putExtra(AddEditTeaActivity.EXTRA_DESCRIPTION, tea.getDescription());
                intent.putExtra(AddEditTeaActivity.EXTRA_PRICE, tea.getPrice());
                startActivityForResult(intent, EDIT_TEA_REQUEST);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TEA_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTeaActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTeaActivity.EXTRA_DESCRIPTION);
            int price = data.getIntExtra(AddEditTeaActivity.EXTRA_PRICE, 1);

            Tea tea = new Tea(title, description, price);
            teaViewModel.insert(tea);

            Toast.makeText(this, "Tea saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TEA_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTeaActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Tea can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditTeaActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTeaActivity.EXTRA_DESCRIPTION);
            int price = data.getIntExtra(AddEditTeaActivity.EXTRA_PRICE, 1);

            Tea tea = new Tea(title, description, price);
            tea.setId(id);
            teaViewModel.update(tea);

            Toast.makeText(this, "Tea updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tea not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.category_menu, menu);
        return true;
    }


    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_teas:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

     */
}

