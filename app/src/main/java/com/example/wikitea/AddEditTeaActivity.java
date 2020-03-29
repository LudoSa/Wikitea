package com.example.wikitea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import static com.example.wikitea.AddEditCategoryActivity.EXTRA_IDCATEGORY;

public class AddEditTeaActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.wikitea.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.wikitea.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.wikitea.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRICE = "com.example.wikitea.EXTRA_PRICE";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerId;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tea);

        //Set action bar
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        //numberPickerId = findViewById(R.id.n)


        //contains id, title, price
        Intent intent = getIntent();

        //Add or edit tea title
        if(intent.hasExtra(EXTRA_ID))
        {
            setTitle("Edit Tea");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            //edit.setText(intent.getStringExtra(EXTRA_ID));

        }
        else
        {
            setTitle("Add Tea");
        }


    }

    private void saveTea()
    {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int price = 0; // = edit  .getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty())
        {
            Toast.makeText(this,"Please insert a title and a description",Toast.LENGTH_SHORT).show();
            return ;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRICE,price);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id != -1)
        {
            data.putExtra(EXTRA_ID,id);
        }

        //if everything runs correctly
        setResult(RESULT_OK, data);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_tea_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_tea:
                saveTea();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    }
