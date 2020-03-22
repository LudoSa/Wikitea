package com.example.wikitea;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wikitea.Tables.Tea;
import com.example.wikitea.Tables.TeaAdapter;
import com.example.wikitea.Tables.TeaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//Class for tea's categories
public class HomeActivity extends AppCompatActivity{

    //Declare the viewModel we'll use
    private TeaViewModel teaViewModel;


    public static final int ADD_TEA_REQUEST = 1;
    public static final int EDIT_TEA_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        FloatingActionButton buttonAddTea = findViewById(R.id.button_add_tea);
        buttonAddTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, AddEditTeaActivity.class);
                startActivityForResult(intent,ADD_TEA_REQUEST);
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

               adapter.setTeas(teas); //Everytime the method update is called, it changed here the list

                //update RecyclerView
                //Toast.makeText(HomeActivity.this,"onChanged",Toast.LENGTH_SHORT).show();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                teaViewModel.delete(adapter.getTeaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(HomeActivity.this,"Tea deleted",Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);


        adapter.setOnItemClickListener(new TeaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tea tea) {
                Intent intent = new Intent(HomeActivity.this, AddEditTeaActivity.class);
                intent.putExtra(AddEditTeaActivity.EXTRA_ID, tea.getId());
                intent.putExtra(AddEditTeaActivity.EXTRA_TITLE, tea.getTitle());
                intent.putExtra(AddEditTeaActivity.EXTRA_DESCRIPTION, tea.getDescription());
                intent.putExtra(AddEditTeaActivity.EXTRA_PRICE, tea.getPrice());

                startActivityForResult(intent,EDIT_TEA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //In order to know which request are sent
        if(requestCode == ADD_TEA_REQUEST && resultCode == RESULT_OK){

            String title = data.getStringExtra(AddEditTeaActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTeaActivity.EXTRA_DESCRIPTION);
            int price = data.getIntExtra(AddEditTeaActivity.EXTRA_PRICE,1);


            Tea tea = new Tea(title,description,price);
            teaViewModel.insert(tea);

            Toast.makeText(this,"Tea saved",Toast.LENGTH_SHORT).show();
        } else if
        (requestCode == EDIT_TEA_REQUEST && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra(AddEditTeaActivity.EXTRA_ID, -1);

            if(id == -1)
            {
                Toast.makeText(this, "Tea couldn't be saved !", Toast.LENGTH_SHORT).show();
            }

            String title = data.getStringExtra(AddEditTeaActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTeaActivity.EXTRA_DESCRIPTION);
            int price = data.getIntExtra(AddEditTeaActivity.EXTRA_PRICE,1);

            Tea tea = new Tea(title,description,price);
            tea.setId(id);
            teaViewModel.update(tea);
            Toast.makeText(this, "Tea updated !", Toast.LENGTH_SHORT).show();

        }
            else
        {
            Toast.makeText(this,"Tea NOT saved !",Toast.LENGTH_SHORT).show();
        }
    }
}
