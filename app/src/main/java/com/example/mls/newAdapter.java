package com.example.mls;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class newAdapter extends RecyclerView.Adapter<newAdapter.RecyclerViewHolder> {

    //FAKE DATA PRO RECYCLER
    Context context;
    ArrayList<excoitem> results = new ArrayList<>();

    public newAdapter(Context c, ArrayList<excoitem> result) {
        context = c;
        results = result;
    }


    @Override
    public newAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.excoitem, parent, false);
        newAdapter.RecyclerViewHolder recyclerViewHolder = new newAdapter.RecyclerViewHolder(view);
        //resultView.MyOnClickListener listener = new resultView().new MyOnClickListener();
        // view.setOnClickListener(listener);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(newAdapter.RecyclerViewHolder holder, final int position) {
        holder.img.setImageBitmap(results.get(position).getBtm());
        holder.name.setText(results.get(position).getName());
        holder.post.setText(results.get(position).getPosition());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, excofullpage.class);
                sharedData.setMyexco(results.get(position));
                /*intent.putParcelableArrayListExtra("item",results);
                intent.putExtra("position",position);*/
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name, post;
        ImageView img;
        View lay;

        public RecyclerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            post = (TextView) view.findViewById(R.id.post);
            img = view.findViewById(R.id.image);
            lay = view.findViewById(R.id.lay);
        }

    }
}
