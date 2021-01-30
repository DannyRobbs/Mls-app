package com.example.mls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class fullscreen extends AppCompatActivity {
    ImageView view;
    ImageButton bit;
    SharedPreferences preferences;

    public fullscreen() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent i = getIntent();
        preferences = getSharedPreferences(sharedData.getInfokey(), MODE_PRIVATE);
        final Uri uri = Uri.parse(i.getStringExtra("uri"));
        bit = findViewById(R.id.download);
        // bit = (Bitmap) i.getParcelableExtra("BitmapImage");
        view = findViewById(R.id.imager);
        bit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(fullscreen.this, "Downloading.", Toast.LENGTH_SHORT).show();
                Picasso.with(fullscreen.this)
                        .load(uri)
                        .into(new Target() {
                                  @Override
                                  public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                      try {
                                          String root = fullscreen.this.getExternalFilesDir("/").getAbsolutePath();
                                          File myDir = new File(root + "/MLSDownloads");

                                          if (!myDir.exists()) {
                                              myDir.mkdirs();
                                          }

                                          String name = new Date().toString() + ".jpg";
                                          myDir = new File(myDir, name);
                                          FileOutputStream out = new FileOutputStream(myDir);
                                          bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                                          out.flush();
                                          out.close();
                                          Toast.makeText(fullscreen.this, "Downloaded.", Toast.LENGTH_SHORT).show();

                                          int download = Integer.parseInt(preferences.getString("downloads", "0"));
                                          download += 1;
                                          SharedPreferences.Editor editor = preferences.edit();
                                          editor.putString("downloads", String.valueOf(download));
                                          editor.apply();
                                          userVerify u = new userVerify(fullscreen.this);
                                          u.execute("update", preferences.getString("matric", "0"), preferences.getString("uploads", "0"), preferences.getString("downloads", "0"));

                                      } catch (Exception e) {
                                          e.printStackTrace();
                                      }
                                  }

                                  @Override
                                  public void onBitmapFailed(Drawable errorDrawable) {

                                  }

                                  @Override
                                  public void onPrepareLoad(Drawable placeHolderDrawable) {
                                  }
                              }
                        );
            }
        });

        try {
            Glide.with(this).load(uri).override(1000, 1000).into(view);
            //  view.setImageBitmap(bit);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void transformimage(){




    }
}
