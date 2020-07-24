package com.example.mls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class fetchloader extends AsyncTaskLoader<List<ResultItem>> {
String check;
    public fetchloader(@NonNull Context context, String c) {

        super(context);check = c;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<ResultItem> loadInBackground() {
        Log.e("doinback","yeah");
        String bytes = check;
        String result = "";

        try {
            URL url = new URL("https://rottenegg.000webhostapp.com/ffetch.php");
            HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStream output = urlConnection.getOutputStream();
            BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(output , "UTF-8"));
            String postdata = URLEncoder.encode("level" , "UTF-8")+ "="+URLEncoder.encode(bytes,"UTF-8");
            bf.write(postdata);
            bf.flush();
            bf.close();
            InputStream input = urlConnection.getInputStream();
            BufferedReader bb = new BufferedReader(new InputStreamReader(input,"iso-8859-1"));
            StringBuilder build = new StringBuilder();

            String line;
            line = bb.readLine();
            while(line !=null){
                result+=line;
                line = bb.readLine();
            }
            input.close();
            bb.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Log.e("result",result);
        ArrayList<ResultItem> you = new ArrayList<>();
        String resultmessage = result;
        try {
            JSONArray ar = new JSONArray(resultmessage);
            for (int i = 0;i<ar.length();i++){
                JSONObject root = ar.getJSONObject(i);
                String img = root.getString("img");
                String label = root.getString("label");
                String level = root.getString("levl");
                /*byte[] b = Base64.decode(img,Base64.URL_SAFE |Base64.NO_WRAP);
                Bitmap bits = BitmapFactory.decodeByteArray(b,0,b.length);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(b,0,b.length,options);

                options.inSampleSize = calculateInSampleSize(options,250,250);
                options.inJustDecodeBounds = false;
                bits = BitmapFactory.decodeByteArray(b,0,b.length,options);*/
                Uri bits = Uri.parse(img);

                you.add(new ResultItem(bits,label,level));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("new",String.valueOf(you.size()));
        // adapter.clear();
        // adapter = new customAdapter(this,you);
        // if(you!=null){

        // }

        // adapter.notifyDataSetChanged();
        return you;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
