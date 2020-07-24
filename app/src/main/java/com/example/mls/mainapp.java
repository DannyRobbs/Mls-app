package com.example.mls;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class mainapp extends AppCompatActivity {
    private NetworkInfo info;
    private ConnectivityManager cn;

ImageView img;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainapp);

    }

    public void Portal(View view){
        /*Uri webpage = Uri.parse("https://studentportal.unilag.edu.ng/");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);*/
        cn =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = cn.getActiveNetworkInfo();
        if(info != null &&
                info.isAvailable() &&
                info.isConnected()){
            Intent intent = new Intent(this, webpage.class);
            intent.putExtra("url",getString(R.string.portal_url));
            intent.putExtra("title",getString(R.string.portal_header));
            startActivity(intent);
        }else{

            AlertDialog.Builder dialog =  new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.alert));
            dialog.setMessage(getString(R.string.network_warning));
            dialog.setNeutralButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog diag = dialog.create();
            diag.show();
        }

    }

    public void contact(View view){
        /*Uri webpage = Uri.parse("https://wa.me/2348104978440");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);*/
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "badeggsofficial@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Complaint: MLS App");

//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

        startActivity(Intent.createChooser(emailIntent, "What App would you like?"));
    }

    public void results(View v){
        Intent intent = new Intent(this, resultView.class);
        startActivity(intent);

    }

    public void quiz(View v){
        Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show();

    }

    public void news(View view){

        Intent intent = new Intent(this, newsActivity.class);
        startActivity(intent);

      /*  Intent intent = new Intent(this, webpage.class);
        intent.putExtra("url",getString(R.string.blogspot_url));
        intent.putExtra("title",getString(R.string.news_header));
        startActivity(intent);*/
    }
    public void study(View v){
        Intent intent = new Intent(this, Studymaterials.class);
        startActivity(intent);

    }
    public void excopro(View v){
        Intent intent = new Intent(this, excofullpage.class);
        intent.putExtra("post","PRO");
        startActivity(intent);

    }
    public void exco(View v){
        Intent intent = new Intent(this, excofullpage.class);
        intent.putExtra("post","president");
        startActivity(intent);

    }
    public void excosoc(View v){
        Intent intent = new Intent(this, excofullpage.class);
        intent.putExtra("post","social");
        startActivity(intent);

    }
    public void excowel(View v){
        Intent intent = new Intent(this, excofullpage.class);
        intent.putExtra("post","welfare");
        startActivity(intent);

    }
    public void excoacad(View v){
        Intent intent = new Intent(this, excofullpage.class);
        intent.putExtra("post","Acad");
        startActivity(intent);

    }
    public void excosport(View v){
        Intent intent = new Intent(this, excofullpage.class);
        intent.putExtra("post","sport");
        startActivity(intent);

    }
    public void excoAGS(View v){
        Intent intent = new Intent(this, excofullpage.class);
        intent.putExtra("post","AGS");
        startActivity(intent);

    }
    public void excoGS(View v){
        Intent intent = new Intent(this, excofullpage.class);
        intent.putExtra("post","GS");
        startActivity(intent);

    }
    public void excovp(View v){
        Intent intent = new Intent(this, excofullpage.class);
        intent.putExtra("post","vp");
        startActivity(intent);

    }
    public void excofin(View v){
        Intent intent = new Intent(this, excofullpage.class);
        intent.putExtra("post","fin");
        startActivity(intent);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

                Intent intent = new Intent(this, aboutus.class);
                startActivity(intent);
             return true;
    }

    }

