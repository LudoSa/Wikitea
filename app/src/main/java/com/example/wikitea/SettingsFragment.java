package com.example.wikitea;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private Switch switch_theme;
    private Button button_aboutApp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create the 2 switch and button
        switch_theme = getView().findViewById(R.id.switchTheme);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            switch_theme.setChecked(true);
        }

        //This button opens the aboutApp fragment
        button_aboutApp = getView().findViewById(R.id.button_aboutApp);
        button_aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //OPEN AboutApp Fragment
                getFragmentManager().beginTransaction().replace(android.R.id.content, new AboutAppFragment()).addToBackStack(null).commit();
            }
        });


        //Switch to change the theme to dark or day
        //Check the switch, on which mode it is checked, then changes it
        switch_theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    //Restart the Settings Fragment, to see the theme updated in no time
    private void restartApp() {
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).addToBackStack(null).commit();
        getActivity().onBackPressed();
    }
}
