package com.example.mls;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    boolean passwordchecked= true;
    EditText matric ;
    EditText password ;
    Button loginbtn;
    private NetworkInfo info;
    private ConnectivityManager cn;
    ProgressBar pbar;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
         matric = (EditText)findViewById(R.id.logmatric);
         password = (EditText)findViewById(R.id.logpass);
         loginbtn = findViewById(R.id.logintoapp);

        cn =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
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
           Toast.makeText(this,getString(R.string.field_empty_prompt), Toast.LENGTH_SHORT).show();
           return;
       }else if(pass == null || pass.equalsIgnoreCase("")){
           // Handle well again!
           Toast.makeText(this,getString(R.string.field_empty_prompt), Toast.LENGTH_SHORT).show();
           return;
       }

       userVerify verify =  new userVerify(this);
       verify.execute(type,matricc,pass);


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
        if(passwordchecked){

           e.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else {
            e.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }



    }
}
