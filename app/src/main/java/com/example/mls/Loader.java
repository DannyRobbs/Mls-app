package com.example.mls;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

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

public class Loader extends AsyncTaskLoader<String> {

    String check, firstname, lastname, matric, level, email, password, type;

    public Loader(@NonNull Context context, String typ, String fname, String lname, String mat, String lvl, String ema, String pass) {

        super(context);
        type = typ;
        firstname = fname;
        lastname = lname;
        matric = mat;
        level = lvl;
        email = ema;
        password = pass;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        String result = "";

        if (type.equalsIgnoreCase("fetch")) {

            try {
                URL url = new URL("https://rotteneggs1.000webhostapp.com/fetch.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStream output = urlConnection.getOutputStream();
                BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
                String postdata = URLEncoder.encode("matric", "UTF-8") + "=" + URLEncoder.encode(matric, "UTF-8");
                bf.write(postdata);
                bf.flush();
                bf.close();
                InputStream input = urlConnection.getInputStream();
                BufferedReader bb = new BufferedReader(new InputStreamReader(input, "iso-8859-1"));
                StringBuilder build = new StringBuilder();

                String line;
                line = bb.readLine();
                while (line != null) {
                    result += line;
                    line = bb.readLine();
                }
                input.close();
                bb.close();
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (type.equalsIgnoreCase("register")) {


            try {
                URL url = new URL("https://rotteneggs1.000webhostapp.com/register.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStream output = urlConnection.getOutputStream();
                BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
                String postdata = URLEncoder.encode("fname", "UTF-8") + "=" + URLEncoder.encode(firstname, "UTF-8") + "&" +
                        URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(lastname, "UTF-8") + "&" +
                        URLEncoder.encode("level", "UTF-8") + "=" + URLEncoder.encode(level, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("matric", "UTF-8") + "=" + URLEncoder.encode(matric, "UTF-8") + "&" +
                        URLEncoder.encode("pass_word", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bf.write(postdata);
                bf.flush();
                bf.close();
                InputStream input = urlConnection.getInputStream();
                BufferedReader bb = new BufferedReader(new InputStreamReader(input, "iso-8859-1"));
                StringBuilder build = new StringBuilder();

                String line;
                line = bb.readLine();
                while (line != null) {
                    result += line;
                    line = bb.readLine();
                }
                input.close();
                bb.close();
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("login")) {

            try {
                URL url = new URL("https://rotteneggs1.000webhostapp.com/login.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStream output = urlConnection.getOutputStream();
                BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
                String postdata = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(matric, "UTF-8") + "&" +
                        URLEncoder.encode("pass_word", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bf.write(postdata);
                bf.flush();
                bf.close();
                InputStream input = urlConnection.getInputStream();
                BufferedReader bb = new BufferedReader(new InputStreamReader(input, "iso-8859-1"));
                StringBuilder build = new StringBuilder();

                String line;
                line = bb.readLine();
                while (line != null) {
                    result += line;
                    line = bb.readLine();
                }
                input.close();
                bb.close();
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

}


