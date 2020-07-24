package com.example.mls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class splashscreen extends AppCompatActivity {
    Runnable runnable;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try
        {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_splashscreen);
        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable,2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null & runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
