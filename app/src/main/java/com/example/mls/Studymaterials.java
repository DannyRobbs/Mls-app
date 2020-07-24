package com.example.mls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Studymaterials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studymaterials);
    }
    public void anatomy(View v){
        Uri webpage = Uri.parse("https://drive.google.com/folderview?id=17wFGOBRPqNF6B1l4qcdHmorxd520teT4");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
    public void phys(View v){
        Uri webpage = Uri.parse("https://drive.google.com/folderview?id=1zMxLYPGdFB254YhCprejXmRwd3V69BQ_");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
    public void biochem(View v){
        Uri webpage = Uri.parse("https://drive.google.com/folderview?id=1U0PMqMkLt3YvCikOIKxi0o6-ZwqzlZLr");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
    public void clin(View v){
        Uri webpage = Uri.parse("https://drive.google.com/folderview?id=1zE089YPol71N7z8dnuS3n20AQtoeJcIl");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
    public void haem(View v){
        Uri webpage = Uri.parse("https://drive.google.com/folderview?id=1m1hnXf5XiEVJGgnBT8p7lRXUm1flSUAo");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
    public void histo(View v){
        Uri webpage = Uri.parse("https://drive.google.com/folderview?id=1VKjfu_ZkotdykQ0-bvw_VYQS-BtfxcDn");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
    public void micro(View v){
        Uri webpage = Uri.parse("https://drive.google.com/folderview?id=1xXEyW6V_xJElxYYtqeqwh3LQjRp1vgkK");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
    public void gene(View v){
        Uri webpage = Uri.parse("https://drive.google.com/folderview?id=14koYWaySkNn599b7YnTrgXC6YQ3NMsYu");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
    public void ment(View v){
        Uri webpage = Uri.parse("https://drive.google.com/folderview?id=1FLlDCspWcA42ktWX-m_InLMg3E1Eez-l");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
    public void other(View v){
        Uri webpage = Uri.parse("https://drive.google.com/folderview?id=1CrkOjR-477IDr8v0WItRFxW1THf98ujS");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);
    }
}
