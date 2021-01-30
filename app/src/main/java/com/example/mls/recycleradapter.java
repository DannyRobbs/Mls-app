package com.example.mls;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
//import com.example.mls.resultView.MyOnClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sg-0036936 on 08/09/2016.
 */
public class recycleradapter extends RecyclerView.Adapter<recycleradapter.RecyclerViewHolder> {

    //FAKE DATA PRO RECYCLER
    Context context;
    ArrayList<ResultItem> results = new ArrayList<>();
    clicked Listner;
    SharedPreferences preferences;

    public recycleradapter(Context c, ArrayList<ResultItem> result, clicked listner) {
        context = c;
        results = result;
        this.Listner = listner;
        preferences = context.getSharedPreferences(sharedData.getInfokey(), Context.MODE_PRIVATE);
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forlist, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        //resultView.MyOnClickListener listener = new resultView().new MyOnClickListener();
   // view.setOnClickListener(listener);

        return recyclerViewHolder;
    }
    public void updateData(ArrayList<ResultItem> viewModels) {
        results.clear();
        results.addAll(viewModels);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.label.setText(results.get(position).getLabel());
        holder.level.setText(results.get(position).getLvl());
        Glide.with(context).load(results.get(position).getBtm()).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultView r = new resultView();
                r.full(context, position);
            }
        });
        holder.level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultView r = new resultView();
                r.full(context, position);
            }
        });
        holder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultView r = new resultView();
                r.full(context, position);
            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Downloading.", Toast.LENGTH_SHORT).show();
                Picasso.with(context)
                        .load(results.get(position).getBtm())
                        .into(new Target() {
                                  @Override
                                  public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                      try {
                                          String root = context.getExternalFilesDir("/").getAbsolutePath();
                                          File myDir = new File(root + "/MLSDownloads");

                                          if (!myDir.exists()) {
                                              myDir.mkdirs();
                                          }

                                          String name = new Date().toString() + ".jpg";
                                          myDir = new File(myDir, name);
                                          FileOutputStream out = new FileOutputStream(myDir);
                                          bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                                          out.flush();
                                          out.close();
                                          Toast.makeText(context, "Downloaded.", Toast.LENGTH_SHORT).show();

                                          int download = Integer.parseInt(preferences.getString("downloads", "0"));
                                          download += 1;
                                          SharedPreferences.Editor editor = preferences.edit();
                                          editor.putString("downloads", String.valueOf(download));
                                          editor.apply();
                                          userVerify u = new userVerify(context);
                                          u.execute("update", preferences.getString("matric", "0"), preferences.getString("uploads", "0"), preferences.getString("downloads", "0"));

                                      } catch (Exception e) {
                                          e.printStackTrace();
                                      }
                                  }

                                  @Override
                                  public void onBitmapFailed(Drawable errorDrawable) {

                                  }

                                  @Override
                                  public void onPrepareLoad(Drawable placeHolderDrawable) {
                                  }
                              }
                        );

            }
        });
    }



    @Override
    public int getItemCount() {
        return results.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView label, level;
        ImageView img;
        ImageButton download;
        View vie;


        public RecyclerViewHolder(View view) {
            super(view);
            label = (TextView) view.findViewById(R.id.resulttxt);
            level = (TextView) view.findViewById(R.id.lvltxt);
            img = view.findViewById(R.id.resultimg);
            download = view.findViewById(R.id.download);
            vie = view.findViewById(R.id.vie);
            label.setOnClickListener(this);
            img.setOnClickListener(this);
            level.setOnClickListener(this);
            vie.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Listner.itemclicked();
        }
    }

    public interface clicked {
        void itemclicked();
    }
}
