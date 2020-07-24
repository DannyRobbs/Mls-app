package com.example.mls;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class matric extends AppCompatActivity {
    EditText Matric;
    Button fetchbtn;
    private NetworkInfo info;
    private ConnectivityManager cn;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matric);
        Matric = findViewById(R.id.matricu);
        fetchbtn = findViewById(R.id.fetch);
        cn =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        fetchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info = cn.getActiveNetworkInfo();
                if(info != null &&
                        info.isAvailable() &&
                        info.isConnected()){
                    getfillinfo();
                }else{
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
    public void getfillinfo(){
        String mat =  Matric.getText().toString();
        String type = getString(R.string.type_fetch);
        if(mat != null || !mat.equalsIgnoreCase("")){
            userVerify userfetch = new userVerify(this);
            userfetch.execute(type,mat);
            // setfillinfo();
        }
    }
}
