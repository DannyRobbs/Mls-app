package com.example.mls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class GetStarted extends AppCompatActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        preferences = getSharedPreferences(sharedData.getInfokey(), MODE_PRIVATE);
        if (!preferences.getString("matric", "null").equalsIgnoreCase("null")) {
            Intent i = new Intent(this, mainapp.class);
            startActivity(i);
            finish();
        }
    }

    public void start(View v) {
        startActivity(new Intent(this, login.class));
    }
}