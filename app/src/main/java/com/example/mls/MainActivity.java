package com.example.mls;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void signup(View view){

        Intent intent = new Intent(this, matric.class);
        startActivity(intent);
    }

    public void login(View view){

        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
    public void free(View view){

        Intent intent = new Intent(this, mainapp.class);
        startActivity(intent);
    }

    public void complain(View view){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "badeggsofficial@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Complaint: MLS App");

//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

        startActivity(Intent.createChooser(emailIntent, "What App would you like?"));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
