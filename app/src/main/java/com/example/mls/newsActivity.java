package com.example.mls;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class newsActivity extends AppCompatActivity {
    private NetworkInfo info;
    private ConnectivityManager cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#03a9f4"));
        }

        try
        {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_news);
    }

    public void nimelssa(View v){
        cn =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = cn.getActiveNetworkInfo();
        if(info != null &&
                info.isAvailable() &&
                info.isConnected()){
            Intent intent = new Intent(this, webpage.class);
            intent.putExtra("url",getString(R.string.blogspot_url));
            intent.putExtra("title",getString(R.string.news_header));
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

    public void mlscn(View v){
        cn =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = cn.getActiveNetworkInfo();
        if(info != null &&
                info.isAvailable() &&
                info.isConnected()){
            Intent intent = new Intent(this, webpage.class);
            intent.putExtra("url",getString(R.string.mlscn_url));
            intent.putExtra("title",getString(R.string.news_header));
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
    public void podcast(View v){
        Intent intent = new Intent(this, podcastsplash.class);
        startActivity(intent);
    }
}
