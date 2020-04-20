package com.example.wikitea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String CHANNEL_1 = "Welcome !";
    private EditText emailView;
    private EditText passwordView;

    private FirebaseAuth fAuth;
    private NotificationManagerCompat notificationManager;
    boolean login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Create the firebaseAuthentification
        fAuth = FirebaseAuth.getInstance();

        //Create the channel, to send notifications
        notificationManager = NotificationManagerCompat.from(this);
        createNotificationChannel();


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
        signInButton.setOnClickListener(v -> attemptLogin());
    }

        //Make sure the admin does exist and writes the right password !
        private void attemptLogin()
        {
            // Store values at the time.
            String email = emailView.getText().toString();
            String password = passwordView.getText().toString();

            //Check if the two fields are filled
            if(email.isEmpty() )
            {
                emailView.setError(getString(R.string.error_empty_field));
            }
            else if (password.isEmpty())
            {
                passwordView.setError(getString(R.string.error_empty_field));
            }

                //Connection, checking also if the email does exists and matches with the password entered
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        sendNotification(email);
                        Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        emailView.setText("");
                        passwordView.setText("");
                    } else {
                        emailView.setError(getString(R.string.error_login));
                        emailView.requestFocus();
                        passwordView.setText("");
                    }
                });

            }


    private void createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }

    public void sendNotification(String email){
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("WikiTea")
                .setContentText("Welcome "+ email +" !")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();


        notificationManager.notify(1, notification);
    }
    }