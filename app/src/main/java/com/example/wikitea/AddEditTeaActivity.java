package com.example.wikitea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
    public static final String EXTRA_ORIGIN = "com.example.wikitea.EXTRA_ORIGIN";
    public static final String EXTRA_IDCATEGORYTEA = "com.example.wikitea.EXTRA_IDCATEGORY";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextOrigin;

    private int favorite;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //DARK/LIGHT THEME
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tea);

        //Set action bar
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextOrigin = findViewById(R.id.edit_text_origin);


        //contains id, title, description, origin
        Intent intent = getIntent();

        //Add or edit tea title
        if(intent.hasExtra(EXTRA_ID))
        {
            setTitle("Edit Tea");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editTextOrigin.setText(intent.getStringExtra(EXTRA_ORIGIN));
        }
        else
        {
            setTitle("Add Tea");
        }
    }


    //Save the tea and send data to TeaActivity for add or edit
    private void saveTea()
    {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String origin = editTextOrigin.getText().toString();


        //Get the categoryId
        Bundle categoryIntent = getIntent().getExtras();
        String categoryId = categoryIntent.getString("EXTRA_CATEGORY_ID");


        if(title.trim().isEmpty() || description.trim().isEmpty())
        {
            Toast.makeText(this,"Please insert a title and a description",Toast.LENGTH_SHORT).show();
            return ;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_ORIGIN,origin);
        data.putExtra(EXTRA_IDCATEGORYTEA, categoryId);

        String id = getIntent().getStringExtra(EXTRA_ID);
        if(id != null)
        {
            data.putExtra(EXTRA_ID,id);
        }

        //if everything runs correctly
        setResult(RESULT_OK, data);
        finish();
    }


//Set the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_tea_menu, menu);
        return true;
    }


    //Menu options
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
