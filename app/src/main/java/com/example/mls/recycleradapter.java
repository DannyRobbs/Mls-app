package com.example.mls;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.example.mls.resultView.MyOnClickListener;

import java.util.ArrayList;

/**
 * Created by sg-0036936 on 08/09/2016.
 */
public class recycleradapter extends RecyclerView.Adapter<recycleradapter.RecyclerViewHolder> {

    //FAKE DATA PRO RECYCLER
    Context context;
    ArrayList<ResultItem> results = new ArrayList<>();
    View.OnClickListener onCardClickListner;

    public recycleradapter(Context c, ArrayList<ResultItem> result) {
        context=c;
        results = result;
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
                r.full(context,position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView label, level;
        ImageView img;

        public RecyclerViewHolder(View view) {
            super(view);
            label = (TextView) view.findViewById(R.id.resulttxt);
            level = (TextView) view.findViewById(R.id.lvltxt);
            img = view.findViewById(R.id.resultimg);
        }

    }

}
