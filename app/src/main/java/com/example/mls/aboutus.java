package com.example.mls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class aboutus extends AppCompatActivity {

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

        setContentView(R.layout.activity_aboutus);

    }
    public void icon(View v){
        Uri webpage = Uri.parse("https://icons8.com");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
    public void source1(View v){
        Uri webpage = Uri.parse("http://straighttalkmd.com/");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
    public void source2(View v){
        Uri webpage = Uri.parse("https://thecurbsiders.com/");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
}
