package com.example.mls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;

public class fullscreen extends AppCompatActivity {
ImageView view;
Bitmap bit;


    public fullscreen() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
       Intent i =getIntent();
      Uri uri = Uri.parse(i.getStringExtra("uri"));
       // bit = (Bitmap) i.getParcelableExtra("BitmapImage");
       view=findViewById(R.id.imager);

        try {
            Glide.with(this).load(uri).override(1000,1000).into(view);
          //  view.setImageBitmap(bit);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void transformimage(){




    }
}
