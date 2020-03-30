package com.example.wikitea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private Switch aSwitch;
    private Button button_aboutApp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //DARK/LIGHT THEM
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        aSwitch=findViewById(R.id.switchTheme);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            aSwitch.setChecked(true);
        }

        button_aboutApp = findViewById(R.id.button2);

        button_aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new AboutAppFragment()).addToBackStack(null).commit();

            }
        });







        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }
                else
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });
    }



    //Restart the Settings Activity, to see the theme updated in no time.
    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);
                finish();
    }
}
