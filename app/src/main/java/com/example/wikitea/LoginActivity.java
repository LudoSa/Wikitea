package com.example.wikitea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailView;
    private EditText passwordView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        fAuth = FirebaseAuth.getInstance();


        //DARK/LIGHT THEME
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Set up the login form.
        emailView = findViewById(R.id.username);
        passwordView = findViewById(R.id.password);


        Button signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> attemptLogin());
    }

        //Make sure the admin does exist and writes the right password !
        private void attemptLogin()
        {
            // Store values at the time.
            String email = emailView.getText().toString();
            String password = passwordView.getText().toString();

            //Connection

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        emailView.setText("");
                        passwordView.setText("");
                    } else {
                        emailView.setError(getString(R.string.error_invalid_email));
                        emailView.requestFocus();
                        passwordView.setText("");
                    }
                }
            });




        }
    }