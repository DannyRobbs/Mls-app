package com.example.mls;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class mainapp extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private NetworkInfo info;
    private ConnectivityManager cn;
    SharedPreferences preferences;
    String matric, level;
    TextView upload, download;
    LoaderManager manager;
    ArrayList<excoitem> list = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    newAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_mainapp);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView = findViewById(R.id.recyclerView);
        upload = findViewById(R.id.upload);
        download = findViewById(R.id.download);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mainapp.this);
                dialog.setTitle("Confirm");
                dialog.setMessage("Do you really want to log out?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("matric");
                        editor.remove("level");
                        editor.remove("uploads");
                        editor.remove("downloads");
                        editor.apply();

                        startActivity(new Intent(mainapp.this, GetStarted.class));
                        finish();
                    }
                });
                dialog.setNegativeButton("Not really", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog diag = dialog.create();
                diag.show();
            }
        });
        createList();
        preferences = getSharedPreferences(sharedData.getInfokey(), MODE_PRIVATE);
        matric = preferences.getString("matric", "null");
        level = preferences.getString("level", "null");
        adapter = new newAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        manager = getSupportLoaderManager();
        manager.initLoader(1, null, this);
    }

    private void createList() {
        list.add(new excoitem(BitmapFactory.decodeResource(getResources(), R.drawable.vp_photo), getString(R.string.vp_name), getString(R.string.vp_DOB), getString(R.string.vp_Level), getString(R.string.vp_Post), getString(R.string.vp_State), getString(R.string.vp_Religion), getString(R.string.vp_email), getString(R.string.vp_phone), getString(R.string.vp_ig), getString(R.string.vp_remark)));
        list.add(new excoitem(BitmapFactory.decodeResource(getResources(), R.drawable.pres), getString(R.string.pre_name), getString(R.string.pre_DOB), getString(R.string.pre_Level), getString(R.string.pre_Post), getString(R.string.pre_State), getString(R.string.pre_Religion), getString(R.string.pre_email), getString(R.string.pre_phone), getString(R.string.pre_ig), getString(R.string.pre_remark)));
        list.add(new excoitem(BitmapFactory.decodeResource(getResources(), R.drawable.fin_sec_photo), getString(R.string.Finsec_name), getString(R.string.Finsec_DOB), getString(R.string.Finsec_Level), getString(R.string.Finsec_Post), getString(R.string.Finsec_State), getString(R.string.Finsec_Religion), getString(R.string.Finsec_email), getString(R.string.Finsec_phone), getString(R.string.Finsec_ig), getString(R.string.Finsec_remark)));
        list.add(new excoitem(BitmapFactory.decodeResource(getResources(), R.drawable.gs), getString(R.string.GS_name), getString(R.string.GS_DOB), getString(R.string.GS_Level), getString(R.string.GS_Post), getString(R.string.GS_State), getString(R.string.GS_Religion), getString(R.string.GS_email), getString(R.string.GS_phone), getString(R.string.GS_ig), getString(R.string.GS_remark)));
        list.add(new excoitem(BitmapFactory.decodeResource(getResources(), R.drawable.ags), getString(R.string.AGS_name), getString(R.string.AGS_DOB), getString(R.string.AGS_Level), getString(R.string.AGS_Post), getString(R.string.AGS_State), getString(R.string.AGS_Religion), getString(R.string.AGS_email), getString(R.string.AGS_phone), getString(R.string.AGS_ig), getString(R.string.AGS_remark)));
        list.add(new excoitem(BitmapFactory.decodeResource(getResources(), R.drawable.acad_sec_photo), getString(R.string.Acad_sec_name), getString(R.string.Acad_sec_DOB), getString(R.string.Acad_sec_Level), getString(R.string.Acad_sec_Post), getString(R.string.Acad_sec_State), getString(R.string.Acad_sec_Religion), getString(R.string.Acad_sec_email), getString(R.string.Acad_sec_phone), getString(R.string.Acad_sec_ig), getString(R.string.Acad_sec_remark)));
        list.add(new excoitem(BitmapFactory.decodeResource(getResources(), R.drawable.social), getString(R.string.welfare_name), getString(R.string.welfare_DOB), getString(R.string.welfare_Level), getString(R.string.welfare_Post), getString(R.string.welfare_State), getString(R.string.welfare_Religion), getString(R.string.welfare_email), getString(R.string.welfare_phone), getString(R.string.welfare_ig), getString(R.string.welfare_remark)));
        list.add(new excoitem(BitmapFactory.decodeResource(getResources(), R.drawable.social_sec_photo), getString(R.string.Social_name), getString(R.string.Social_DOB), getString(R.string.Social_Level), getString(R.string.Social_Post), getString(R.string.Social_State), getString(R.string.Social_Religion), getString(R.string.Social_email), getString(R.string.Social_phone), getString(R.string.Social_ig), getString(R.string.Social_remark)));
        list.add(new excoitem(BitmapFactory.decodeResource(getResources(), R.drawable.sport_sec_photo), getString(R.string.Sport_sec_name), getString(R.string.Sport_sec_DOB), getString(R.string.Sport_sec_Level), getString(R.string.Sport_sec_Post), getString(R.string.Sport_sec_State), getString(R.string.Sport_sec_Religion), getString(R.string.Sport_sec_email), getString(R.string.Sport_sec_phone), getString(R.string.Sport_sec_ig), getString(R.string.Sport_sec_remark)));
        list.add(new excoitem(BitmapFactory.decodeResource(getResources(), R.drawable.pro), getString(R.string.Pro_name), getString(R.string.Pro_DOB), getString(R.string.Pro_Level), getString(R.string.Pro_Post), getString(R.string.Pro_State), getString(R.string.Pro_Religion), getString(R.string.Pro_email), getString(R.string.Pro_phone), getString(R.string.Pro_ig), getString(R.string.Pro_remark)));

    }

    public void Portal(View view) {
        /*Uri webpage = Uri.parse("https://studentportal.unilag.edu.ng/");
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(i);*/
        cn = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = cn.getActiveNetworkInfo();
        if (info != null &&
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

    public void quiz(View v) {
        Intent intent = new Intent(this, webpage.class);
        intent.putExtra("url", "http://nimelssaquizmaster.herokuapp.com");
        intent.putExtra("title", getString(R.string.nimelssa_quiz));
        startActivity(intent);

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

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new com.example.mls.Loader(this, "fetch", "", "", matric, "", "", "");

    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (!data.equalsIgnoreCase(" invalid matric or password") && !data.isEmpty()) {
            try {
                JSONArray arr = new JSONArray(data);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject root = arr.getJSONObject(i);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("uploads", root.getString("upload"));
                    editor.putString("downloads", root.getString("downloads"));
                    editor.apply();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            upload.setText(preferences.getString("uploads", "0"));
            download.setText(preferences.getString("downloads", "0"));
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.restartLoader(1, null, this);
    }
}

