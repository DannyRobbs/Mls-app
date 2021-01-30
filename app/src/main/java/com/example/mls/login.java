package com.example.mls;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    boolean passwordchecked = true;
    EditText matric;
    EditText password;
    SharedPreferences preferences;
    Button loginbtn;
    private NetworkInfo info;
    private ConnectivityManager cn;
    ProgressBar pbar;
    ImageView view;
    LoaderManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_signin);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        matric = (EditText) findViewById(R.id.logmatric);
        view = findViewById(R.id.backarrow);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        password = (EditText) findViewById(R.id.logpass);
        loginbtn = findViewById(R.id.logintoapp);
        manager = getSupportLoaderManager();
        preferences = getSharedPreferences(sharedData.getInfokey(), MODE_PRIVATE);
        if (!preferences.getString("matric", "null").equalsIgnoreCase("null")) {
            Intent i = new Intent(this, mainapp.class);
            startActivity(i);
            finish();
        }
        pbar = findViewById(R.id.progress);
        cn = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetwork();
            }
        });




    }

    public void loginapp(){
        String type = getString(R.string.type_login);
       String matricc =  matric.getText().toString().trim();
       String pass = password.getText().toString().trim();
       if(matricc == null || matricc.equalsIgnoreCase("")){

           // Handle this well
           Toast.makeText(this, getString(R.string.field_empty_prompt), Toast.LENGTH_SHORT).show();
           return;
       } else if (pass == null || pass.equalsIgnoreCase("")) {
           // Handle well again!
           Toast.makeText(this, getString(R.string.field_empty_prompt), Toast.LENGTH_SHORT).show();
           return;
       }

        manager.restartLoader(1, null, this);
        pbar.setVisibility(View.VISIBLE);
        loginbtn.setBackgroundColor(Color.parseColor("#eeeeee"));
        loginbtn.setEnabled(false);

       /* Intent i = new Intent(this, mainapp.class);
        startActivity(i);*/
    }
    public void turnoffprogressbar(){
        pbar.setVisibility(View.GONE);
    }
    private void checkNetwork(){
        info = cn.getActiveNetworkInfo();
        if(info != null &&
                info.isAvailable() &&
                info.isConnected()){
            loginapp();
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


    //To Alter the state of the passwordchecked variable and call showpass
    public void passClick(View view){
        if(!passwordchecked){
            passwordchecked=true;
        }
        else {
            passwordchecked=false;
        }
        showpass();
    }


    // To change password from encrypted to visible to front-end users
    public void showpass(){
        EditText e = (EditText)findViewById(R.id.logpass);
        if (passwordchecked) {

            e.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            e.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }


    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String matricc = matric.getText().toString().trim();
        String pass = password.getText().toString().trim();
        return new com.example.mls.Loader(this, "login", "", "", matricc, "", "", pass);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        pbar.setVisibility(View.GONE);
        loginbtn.setBackground(getResources().getDrawable(R.drawable.login_btn));
        loginbtn.setEnabled(true);
        if (!data.equalsIgnoreCase(" Invalid matric or password")) {
            Log.e("data", data);
            try {
                JSONArray array = new JSONArray(data);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject root = array.getJSONObject(i);
                    String matric = root.getString("matric");
                    String level = root.getString("level");
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("matric", matric);
                    editor.putString("level", level);
                    editor.apply();
                    Log.e("data", matric);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(this, mainapp.class);
            startActivity(i);
            finish();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.alert));
            dialog.setMessage("Invalid Matric or Password.");
            dialog.setNeutralButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            AlertDialog diag = dialog.create();
            diag.setCancelable(true);

            diag.show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void openSignup(View v) {
        startActivity(new Intent(this, matric.class));
    }
}
