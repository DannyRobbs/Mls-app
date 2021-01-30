package com.example.mls;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

public class matric extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    EditText Matric;
    Button fetchbtn;
    private NetworkInfo info;
    String firstname, lastname, level, email, password, matri, status;
    private ConnectivityManager cn;
    LoaderManager manager;
    ProgressBar pro;
    ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_matric);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Matric = findViewById(R.id.matricu);
        fetchbtn = findViewById(R.id.fetch);
        view = findViewById(R.id.backarrow);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        manager = getSupportLoaderManager();
        pro = findViewById(R.id.progress);
        cn = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        fetchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info = cn.getActiveNetworkInfo();
                if (info != null &&
                        info.isAvailable() &&
                        info.isConnected()) {
                    getfillinfo();
                } else {
                    AlertDialog.Builder dialog =  new AlertDialog.Builder(matric.this);
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
        });
    }

    public void getfillinfo() {
        String mat = Matric.getText().toString();
        String type = getString(R.string.type_fetch);
        if (mat != null && !mat.equalsIgnoreCase("")) {
            // userVerify userfetch = new userVerify(this);
            // userfetch.execute(type,mat);
            manager.restartLoader(1, null, this);
            pro.setVisibility(View.VISIBLE);
            fetchbtn.setBackgroundColor(Color.parseColor("#eeeeee"));
            fetchbtn.setEnabled(false);
            // setfillinfo();
        } else {
            Toast.makeText(matric.this, "Cannot be empty.", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new com.example.mls.Loader(this, "fetch", "", "", Matric.getText().toString(), "", "", "");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        pro.setVisibility(View.GONE);
        fetchbtn.setBackground(getResources().getDrawable(R.drawable.login_main_btn));
        fetchbtn.setEnabled(true);
        Log.e("fhfj", data);
        if (!data.equalsIgnoreCase(" invalid matric or password") && !data.isEmpty()) {
            try {
                JSONArray arr = new JSONArray(data);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject root = arr.getJSONObject(i);
                    firstname = root.getString("firstname");
                    lastname = root.getString("lastname");
                    level = root.getString("level");
                    matri = root.getString("matric");
                    email = root.getString("email");
                    password = root.getString("password");
                    status = root.getString("status");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, signup.class);
            intent.putExtra("fname", firstname);
            intent.putExtra("lname", lastname);
            intent.putExtra("lvl", level);
            intent.putExtra("mat", matri);
            intent.putExtra("email", email);
            intent.putExtra("pass", password);
            intent.putExtra("status", status);

            startActivity(intent);
            finish();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(matric.this);
            dialog.setTitle("OOPS!");
            dialog.setMessage("This Matric number is not in our Database.");
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

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


}
