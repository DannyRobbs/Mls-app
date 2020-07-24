package com.example.mls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class customAdapter extends ArrayAdapter<ResultItem> {
    ArrayList<ResultItem> results = new ArrayList<>();
    public customAdapter(@NonNull Context context, ArrayList<ResultItem> result) {

        super(context, 0, result);
        results = result;
    }
    public void swapItems(ArrayList<ResultItem> items) {
        this.results = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
          view =  LayoutInflater.from(getContext()).inflate(R.layout.forlist,parent,false);
        }
       ResultItem r = getItem(position);


        ImageView resimg = view.findViewById(R.id.resultimg);
        Glide.with(getContext()).load(r.getBtm()).override(250,250).into(resimg);
        TextView txt = view.findViewById(R.id.resulttxt);
        txt.setText(r.getLabel());
        TextView xt = view.findViewById(R.id.lvltxt);
        xt.setText(r.getLvl());

        return view;
    }
}
