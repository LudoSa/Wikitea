package com.example.wikitea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_USERNAME = "com.example.myapplication.USERNAME";
    public static final String EXTRA_PASSWORD = "com.example.myapplication.PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void login (View v){

        EditText usernameText = (EditText) findViewById(R.id.editText5);
        EditText passwordText = (EditText) findViewById(R.id.editText);
        Button b1 = (Button) findViewById(R.id.button);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();


        if (username.equals("test") && (password.equals("test"))){

            Intent intent = new Intent(v.getContext(), HomeActivity.class);
            startActivity(intent);


        }

    }



}
