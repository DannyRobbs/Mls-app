package com.example.mls;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;

public class ResultItem extends ArrayList<ResultItem> {
    private Uri btm;
    private String label;
    private String lvl;

    public ResultItem(Uri bt, String name,String level) {
        btm = bt;
        label = name;
        lvl = level;
    }
    public ResultItem() {

    }

    public Uri getBtm() {
        return btm;
    }

    public String getLabel() {
        return label;
    }

    public String getLvl() {
        return lvl;
    }
}
