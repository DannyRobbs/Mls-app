
package com.example.mls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class podcastsplash extends AppCompatActivity {
    Runnable runnable;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcastsplash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        try
        {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_podcastsplash);
        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),podcasthome.class));
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
