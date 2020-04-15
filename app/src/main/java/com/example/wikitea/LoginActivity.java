package com.example.wikitea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.wikitea.Tables.Admin.Admin;
import com.example.wikitea.Tables.Admin.AdminAdapter;
import com.example.wikitea.Tables.Admin.AdminRepository;
import com.example.wikitea.Tables.Admin.AdminViewModel;
import com.example.wikitea.Tables.Category.Category;
import com.example.wikitea.Tables.Category.CategoryAdapter;
import com.example.wikitea.Tables.Category.CategoryViewModel;
import com.example.wikitea.Tables.Tea.TeaViewModel;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText nameView;
    private EditText passwordView;
    private AdminViewModel adminViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        //YO LUDO
        //TEST 1ST PUSH WITH A NEW BRANCH




        //DARK/LIGHT THEME
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Set up the login form.
        nameView = findViewById(R.id.username);
        passwordView = findViewById(R.id.password);


        Button signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> attemptLogin());
    }

        //Make sure the admin does exist and writes the right password !
        private void attemptLogin()
        {
            // Store values at the time.
            String name = nameView.getText().toString();
            String password = passwordView.getText().toString();

            //Get the repository
            AdminViewModel.Factory factory = new AdminViewModel.Factory(getApplication(), name);
            adminViewModel = ViewModelProviders.of(this, factory).get(AdminViewModel.class);

            //Get the admin by his name
            adminViewModel.getAdminByName(name).observe(LoginActivity.this, admin -> {

                if (admin != null)
                {
                    //Test if it's the Right password
                    if (admin.getPassword().equals(password))
                    {
                        //If yes, start the category's activity
                        Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        nameView.setText("");
                        passwordView.setText("");
                    } else
                        {
                        passwordView.setError(getString(R.string.error_invalid_password));
                        passwordView.requestFocus();
                        passwordView.setText("");
                    }

                } else
                    {
                    nameView.setError(getString(R.string.error_invalid_email));
                    nameView.requestFocus();
                    passwordView.setText("");
                    }
            });
        }
    }