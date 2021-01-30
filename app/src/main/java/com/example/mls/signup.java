package com.example.mls;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;

public class signup extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    EditText FirstName;
    EditText LastName;
    EditText Matric;
    EditText Email;
    EditText Password, Status;
    ImageView view;
    Button registerbtn;
    private NetworkInfo info;
    private ConnectivityManager cn;
    Spinner lspin;
    ProgressBar p;
    LoaderManager manager;
    String name, lname, emaill, matric, password, levl, status;
    ArrayList<String> spinnerArray;


    //  Bundle extras = getIntent().getExtras();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_signup);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FirstName = findViewById(R.id.f_name);
        Status = findViewById(R.id.status);
        view = findViewById(R.id.backarrow);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent in = getIntent();
        p = findViewById(R.id.progress);
        manager = getSupportLoaderManager();
        name = in.getStringExtra("fname");
        lname = in.getStringExtra("lname");
        emaill = in.getStringExtra("email");
        password = in.getStringExtra("pass");
        levl = in.getStringExtra("lvl");
        matric = in.getStringExtra("mat");
        status = in.getStringExtra("status");
        Log.e("im here", name);


        LastName = findViewById(R.id.s_name);
        Matric = findViewById(R.id.matric);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        registerbtn = findViewById(R.id.register);
        cn =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);



        spinnerArray = new ArrayList<>();
        spinnerArray.add(getString(R.string.Level200));
        spinnerArray.add(getString(R.string.Level300));
        spinnerArray.add(getString(R.string.Level400));
        spinnerArray.add(getString(R.string.Level500));


        ArrayAdapter<String> spinner = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_dropdown_item,spinnerArray
        );
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

         lspin =(Spinner)findViewById(R.id.spinner);
        lspin.setAdapter(spinner);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              checkNetwork();
            }
        });
       setfillinfo();

    }



    private void checkNetwork(){
        info = cn.getActiveNetworkInfo();
        if(info != null &&
        info.isAvailable() &&
        info.isConnected()){
            registerStudent();
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
    private void registerStudent(){
        String type = getString(R.string.type_register);

        String fname = FirstName.getText().toString().trim();
        String lname = LastName.getText().toString().trim();
        String matric = Matric.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String level =lspin.getSelectedItem().toString();
        if(fname == null || fname.equalsIgnoreCase("")) {

            // Handle this well
            Toast.makeText(signup.this, getString(R.string.first_name_empty_prompt), Toast.LENGTH_SHORT).show();
            return;
        }else if(lname == null || lname.equalsIgnoreCase("")) {

            // Handle this well
            Toast.makeText(signup.this, getString(R.string.last_name_empty_prompt), Toast.LENGTH_SHORT).show();
            return;
        }else if(matric == null || matric.equalsIgnoreCase("")) {

            // Handle this well
            Toast.makeText(signup.this, getString(R.string.matric_empty_prompt), Toast.LENGTH_SHORT).show();
            return;
        }else if(email == null || email.equalsIgnoreCase("")) {

            // Handle this well
            Toast.makeText(signup.this, getString(R.string.email_empty_prompt), Toast.LENGTH_SHORT).show();
            return;
        } else if (password == null || password.equalsIgnoreCase("")) {

            // Handle this well
            Toast.makeText(signup.this, getString(R.string.password_empty_prompt), Toast.LENGTH_SHORT).show();
            return;
        }

        //  userVerify userregister = new userVerify(signup.this);
        // userregister.execute(type,fname,lname,matric,email,level,password);
        manager.restartLoader(1, null, this);
        p.setVisibility(View.VISIBLE);
        registerbtn.setVisibility(View.GONE);

    }

    public void setfillinfo(){

        Log.e("im here","in here");


     //   String na = in.getStringExtra("fname");
            if(password.equalsIgnoreCase(getString(R.string.default_password))) {
                FirstName.setText(name);
                LastName.setText(lname);
                Status.setHint(status);
                Status.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Status.getText().clear();

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        s.clear();

                    }
                });
                // Email.setText(emaill);
                int spinnerPosition = spinnerArray.indexOf(levl);
                lspin.setSelection(spinnerPosition);
                Matric.setText(matric);
            }else{
                AlertDialog.Builder dialog =  new AlertDialog.Builder(this);
                dialog.setTitle(getString(R.string.alert));
                dialog.setMessage(getString(R.string.password_linked_error));
                dialog.setNeutralButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                });
                AlertDialog diag = dialog.create();
                diag.setCancelable(false);

                diag.show();
            }


        // LastName.setText(strings[1]);
        //set level to accurate level
        //  Email.setText(strings[2]);
        // Password.setText(strings[3]);

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String fname = FirstName.getText().toString().trim();
        String lname = LastName.getText().toString().trim();
        String matric = Matric.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String level = lspin.getSelectedItem().toString();
        return new com.example.mls.Loader(this, "register", fname, lname, matric, level, email, password);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        p.setVisibility(View.GONE);
        registerbtn.setVisibility(View.VISIBLE);
        if (data.equalsIgnoreCase(" Updated Successfully!")) {
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Signup Failed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


    // change all unclicked back to default borders
   /* public void unclick(){
       etext.setBackgroundResource(R.drawable.text_field);
        text.setBackgroundResource(R.drawable.text_field);
        mtext.setBackgroundResource(R.drawable.text_field);
        emailtext.setBackgroundResource(R.drawable.text_field);

    }*/
}
