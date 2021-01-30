package com.example.mls;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class resultView extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ResultItem>>, recycleradapter.clicked {
    ListView list;
    static RecyclerView recycler;
    recycleradapter adapterr;
    FloatingActionButton floatingActionButton, fab2, fab3, fab4, fab5;

    View lay;
    RelativeLayout relativeLayout;

    View empt;
    static ArrayList<ResultItem> resultinfo = new ArrayList<>();
    private static final int picker = 1;
    FloatingActionButton btn;
    customAdapter adapter;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] bytes;
    ProgressBar prog;
    ArrayList<String> spinnerArray;
    Spinner lspin;
    TextView wait;
    Context context;
    Button gobtn;
    boolean ischecked = false;
    private NetworkInfo info;
    private ConnectivityManager cn;
    StaggeredGridLayoutManager manager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Fresco.initialize(getApplicationContext());
        // list = findViewById(R.id.listvieww);
        empt = findViewById(R.id.empty);
        context = this;
        floatingActionButton = findViewById(R.id.fabn);
        lay = findViewById(R.id.lay);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });
        // list.setEmptyView(empt);

        relativeLayout = findViewById(R.id.relative);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closepopup();
            }
        });

        recycler = findViewById(R.id.recycler);
        recycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null) {
                    // tapped on child
                    return false;
                } else {
                    closepopup();
                    return true;
                }
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        adapterr = new recycleradapter(this, resultinfo, this);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapterr);


        wait = findViewById(R.id.waittv);
        prog = findViewById(R.id.progb);
        btn = findViewById(R.id.fab);
        prog.setVisibility(View.GONE);
        wait.setVisibility(View.GONE);
        empt.setVisibility(View.GONE);
        adapter = new customAdapter(getApplicationContext(),resultinfo);
       /* list.setAdapter(adapter);
       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResultItem r = (ResultItem) parent.getItemAtPosition(position);





                Intent i = new Intent(getApplicationContext(),fullscreen.class);
               i.putExtra("uri",r.getBtm().toString());
               startActivity(i);


            }
        });*/
        spinnerArray = new ArrayList<>();
        spinnerArray.add(getString(R.string.Level200));
        spinnerArray.add(getString(R.string.Level300));
        spinnerArray.add(getString(R.string.Level400));
        spinnerArray.add(getString(R.string.Level500));

        final LoaderManager m = getSupportLoaderManager();
        cn =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = cn.getActiveNetworkInfo();
        if(info != null &&
                info.isAvailable() &&
                info.isConnected()){
            m.initLoader(1,null,resultView.this);
        }else{

            AlertDialog.Builder dialog =  new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.alert));
            dialog.setMessage(getString(R.string.network_warning));
            dialog.setNeutralButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            AlertDialog diag = dialog.create();
            diag.show();
        }

       /* gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/


        final ArrayAdapter<String> spinner = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, spinnerArray
        );
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lspin = (Spinner) findViewById(R.id.spinners);
        lspin.setAdapter(spinner);
        lspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             /*   ischecked = true;
                m.restartLoader(1,null,resultView.this);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        fab4 = findViewById(R.id.fab4);
        fab5 = findViewById(R.id.fab5);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lspin.setSelection(spinner.getPosition(getString(R.string.Level200)));
                ischecked = true;
                m.restartLoader(1, null, resultView.this);
                closepopup();
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lspin.setSelection(spinner.getPosition(getString(R.string.Level300)));
                ischecked = true;
                m.restartLoader(1, null, resultView.this);
                closepopup();
            }
        });
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lspin.setSelection(spinner.getPosition(getString(R.string.Level400)));
                ischecked = true;
                m.restartLoader(1, null, resultView.this);
                closepopup();
            }
        });
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lspin.setSelection(spinner.getPosition(getString(R.string.Level500)));
                ischecked = true;
                m.restartLoader(1, null, resultView.this);
                closepopup();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // transformimage(bytes);
                // resultpage();
                Intent tent = new Intent(getApplicationContext(), levelinfo.class);
                startActivity(tent);
                // finish();

                //  fetchresults();
            }
        });


    }

    private void popup() {
        if (lay.getVisibility() == View.VISIBLE) {
            lay.setVisibility(View.GONE);
        } else {
            lay.setVisibility(View.VISIBLE);
        }
    }

    private void closepopup() {
        if (lay.getVisibility() == View.VISIBLE) {
            lay.setVisibility(View.GONE);
        }
    }

    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    private void fetchresults() {
        String type = "fetchresult";
        userVerify u = new userVerify(this);
        u.execute(type);
    }

    public void parse(String errormessage){
        int fetchint = 0;

        ArrayList<ResultItem> you = new ArrayList<>();
       String resultmessage = errormessage;
        try {
            JSONArray ar = new JSONArray(resultmessage);
            for (int i = 0;i<ar.length();i++){
                JSONObject root = ar.getJSONObject(i);
                String img = root.getString("img");
                String label = root.getString("label");
                String level = root.getString("levl");
              //  byte[] b = Base64.decode(img,Base64.URL_SAFE |Base64.NO_WRAP);
              //  Bitmap bits = BitmapFactory.decodeByteArray(b,0,b.length);
                Uri bits = Uri.parse(img);
                you.add(new ResultItem(bits,label,level));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("new",String.valueOf(you.size()));
       //adapter.clear();
       // adapter = new customAdapter(this,you);
       // if(you!=null){
           adapter.addAll(you);
           adapterr.updateData(you);

       // }

       // adapter.notifyDataSetChanged();

    }



    public resultView(ArrayList<ResultItem> j) {
        resultinfo = j;
    }
    public resultView() {

    }




    @NonNull
    @Override
    public Loader<List<ResultItem>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.e("oncredateloader","yeah");
        prog.setVisibility(View.VISIBLE);
        wait.setVisibility(View.VISIBLE);
        empt.setVisibility(View.GONE);
        if(ischecked){
            String h = lspin.getSelectedItem().toString();
            return new fetchloader(this,h);

        }
        else{

            return new fetchloader(this,"200 Level");
        }

    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<ResultItem>> loader, List<ResultItem> data) {
        Log.e("onfinloader","yeah");
        adapter.clear();

        if(data!=null && data.size()>0){
            prog.setVisibility(View.GONE);
            wait.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            adapter.addAll(data);
            adapterr.updateData((ArrayList<ResultItem>) data);
            data.clear();




        }else{
            Log.e("nothing to see here","Hes right");
            prog.setVisibility(View.GONE);
            wait.setVisibility(View.GONE);
            empt.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        }



    }
    public void full(Context c, int p){
       // int itemPosition = recycler.getChildPosition(v);
        String s =resultinfo.get(p).getBtm().toString();
        Log.e("mkmkmkm",s);
        Intent i = new Intent(c, fullscreen.class);
        i.putExtra("uri", s);
        c.startActivity(i);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<ResultItem>> loader) {
        adapter.clear();

    }

    @Override
    public void itemclicked() {
        closepopup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        closepopup();
    }
}
