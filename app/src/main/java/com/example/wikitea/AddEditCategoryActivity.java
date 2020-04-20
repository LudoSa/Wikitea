package com.example.wikitea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditCategoryActivity extends AppCompatActivity {

    public static final String EXTRA_IDCATEGORY = "com.example.wikitea.EXTRA_IDCATEGORY";
    public static final String EXTRA_NAME = "com.example.wikitea.EXTRA_NAME";
    public static final String EXTRA_VIRTUES = "com.example.wikitea.EXTRA_VIRTUES";

    private EditText editTextName;
    private EditText editTextVirtues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //DARK/LIGHT THEME
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        editTextName = findViewById(R.id.edit_text_name);
        editTextVirtues = findViewById(R.id.edit_text_virtues);

        //Set actionbar
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get the intent with all data
        Intent intent = getIntent();


        if (intent.hasExtra(EXTRA_IDCATEGORY)){
            setTitle("Edit Category");
            editTextName.setText(intent.getStringExtra(EXTRA_IDCATEGORY));
            editTextVirtues.setText(intent.getStringExtra(EXTRA_VIRTUES));
        }else{
            setTitle("Add Category");
        }
    }

    private void saveCategory()
    {
        String name = editTextName.getText().toString();
        String virtue = editTextVirtues.getText().toString();

        if(name.trim().isEmpty() || virtue.trim().isEmpty())
        {
            Toast.makeText(this,"Please insert a name and a virtue / virtues",Toast.LENGTH_SHORT).show();
            return ;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME,name);
        data.putExtra(EXTRA_VIRTUES,virtue);

        String id = getIntent().getStringExtra(EXTRA_IDCATEGORY);
        if(id != null)
        {
            data.putExtra(EXTRA_IDCATEGORY,id);
        }

        //if everything runs correctly
        setResult(RESULT_OK, data);
        finish();
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_category_menu, menu);

        //Show the menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_category:
                saveCategory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
