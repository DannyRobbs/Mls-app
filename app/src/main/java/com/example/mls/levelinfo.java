package com.example.mls;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class levelinfo extends AppCompatActivity {
    Button btn;
    ByteArrayOutputStream byteArrayOutputStream;
    byte [] bytes = new byte[2048] ;
    private static final int picker = 1;
    ArrayList<ResultItem> resultinfo = new ArrayList<>();
    android.app.LoaderManager man;
    EditText label;
    EditText level;
    String downloadURL;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    Runnable runnable;
    Handler handler;
    private static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelinfo);
        btn = findViewById(R.id.getlvl);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait while we process things...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        label = findViewById(R.id.labelet);
        level = findViewById(R.id.levelet);
        FirebaseApp.initializeApp(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                String n = level.getText().toString().trim();
                if(n!=null && !n.equalsIgnoreCase("")){
                    n=label.getText().toString().trim();
                    if(n!=null && !n.equalsIgnoreCase("")){
                        resultpage();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),getString(R.string.label_empty_prompt),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),getString(R.string.level_empty_prompt),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void resultpage(/*View v*/){
        Intent i = new Intent();
        i.setType(getString(R.string.type_for_result_intent));
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,getString(R.string.select_an_image)),picker);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == picker && resultCode == RESULT_OK){
            Uri filePath = data.getData();
            Uri uri = data.getData();
            Log.e("helo",uri.toString());
            try {
                Bitmap bitmp = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                //  img.setImageBitmap(bitmp);
               // resultinfo.add(new ResultItem(bitmp,"Testing","300"));

               FirebaseUser user = mAuth.getCurrentUser();
            //   progressDialog.setProgress(100);

                if (user != null) {
                    // do your stuff
                    progressDialog.show();
                    uploadtask(filePath);


                } else {
                   signInAnonymously(filePath);
                    //Log.e("yelp","no sign in");
                }

               /* runnable = new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        upload();
                    }
                };
                handler = new Handler();
                handler.postDelayed(runnable,15000);*/




                Log.e("yelp",String.valueOf(resultinfo.size()));
                Bitmap b = getResizedBitmap(bitmp,1500);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                byteArrayOutputStream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                bytes = byteArrayOutputStream.toByteArray();





            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null & runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    public void uploadtask(Uri filePath){
        final StorageReference Sref= storageReference.child("post_images").child(System.currentTimeMillis()+"."+ MimeTypeMap.getFileExtensionFromUrl(filePath.toString()));;

        UploadTask uploadTask= Sref.putFile(filePath);
        Log.e("not successful", "downloadURL");

        Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Could not upload File "+task.getException().getMessage(),Toast.LENGTH_LONG  ).show();
                }

                return Sref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                //getting the post image download url
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    downloadURL = downloadUri.toString();

                    Log.e("Download Url", downloadURL);
                    //  final Uri downloadUrl = null;
                    //Toast.makeText(getApplicationContext(), "Succesfully Uploaded", Toast.LENGTH_SHORT).show();

                    if(downloadURL!=null){
                        progressDialog.dismiss();
                        upload();
                    }else{
                        Log.e("Download Url", "null");
                    }
                }
            }
        });

    }

    private void signInAnonymously(final Uri u) {
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
                Log.e("TAG", "signingInAnonymously");
                uploadtask(u);

            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("TAG", "signInAnonymously:FAILURE", exception);
                    }
                });
    }




    private void upload() {
        String type = getString(R.string.type_uploadresult);
        userVerify user = new userVerify(this);

        String n = new String(Base64.encodeToString(bytes,Base64.URL_SAFE));
       // Log.e("Masterpiece",downloadURL);
resultView res = new resultView(resultinfo);
        Toast.makeText(getApplicationContext(),getString(R.string.uploading_toast),Toast.LENGTH_LONG).show();
        switch (level.getText().toString().trim()){
            case "200":
            case "300":
            case "400":
            case "500":
                user.execute(type,downloadURL,label.getText().toString().trim(),level.getText().toString().trim()+" Level");
                break;
            default:
                Toast.makeText(getApplicationContext(),getString(R.string.invalid_level),Toast.LENGTH_LONG).show();
                break;
        }




    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize){
                    int width = image.getWidth();
                    int height = image.getHeight();

                    float bitmapRatio = (float) width / (float) height;
                    if (bitmapRatio > 1) {
                        width = maxSize;
                        height = (int) (width / bitmapRatio);
                    } else {
                        height = maxSize;
                        width = (int) (height * bitmapRatio);
                    }

                    return Bitmap.createScaledBitmap(image, width, height, true);
                }
            }
