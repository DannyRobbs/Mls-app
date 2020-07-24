package com.example.mls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class userVerify extends AsyncTask<String,String,String> {
    private Context context;
    AlertDialog.Builder a;
    int counter = 0;
    String firstname;
    String lastname;
    String matric;
    String level;
    String password;
    String email;
    String resultmessage;
    ArrayList<String> you = new ArrayList<>();


    public userVerify(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        a = new AlertDialog.Builder(context);
        a.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String s) {
        String errormessage;

        if(s!=null && !s.equalsIgnoreCase("")){
            errormessage = s.trim();
        }else{
            Toast.makeText(context,"Oops, An error Occurred.",Toast.LENGTH_LONG).show();
            return;
        }

       Log.e("POST EXEC", errormessage);









        if(counter ==1){
           // Log.e("POST EXEC", s);
           // logy.turnoffprogressbar();
            if(errormessage.equalsIgnoreCase("Invalid Matric or Password")){


                a.setMessage(s);
                a.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog ai = a.create();
                ai.show();
            }else{
                Intent i = new Intent(context, mainapp.class);
                 context.startActivity(i);
            }


        }else if(counter == 2){

            Log.e("POST EXEC", s);
            Toast.makeText(context,s,Toast.LENGTH_LONG).show();
        }else if(counter ==3){
            int fetchint = 0;
            resultmessage = errormessage;
           /* try {
                JSONArray AR = new JSONArray(resultmessage);
                for (int i = 0; i <AR.length() ; i++) {
                    JSONObject ob = AR.getJSONObject(i);
                    String name = ob.getString("FIRST_NAME");
                    Log.e("flick", name);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("flickerror", e.toString());
            }*/
           if (!resultmessage.equalsIgnoreCase("'null'")){
               for (int j = 8; j < resultmessage.length(); j++) {
                   if(resultmessage.charAt(j) == ':'){

                       String name = "";
                       j++;
                       j++;
                       while(resultmessage.charAt(j)!= '"' ){
                           name+=resultmessage.charAt(j);

                           j++;
                           fetchint++;

                       }Log.e("herere",name );

                       you.add(name);
                    /*switch (fetchint){
                        case 1:
                            firstname = name;
                            break;
                        case 2:
                            lastname = name;
                            break;
                        case 3:
                            level = name;
                            break;
                        case 4:
                            matric = name;
                            break;
                        case 5:
                            email = name;
                            break;
                        case 6:
                            password = name;
                            break;
                        default:
                            break;

                    }*/

                   }

               }

               firstname = you.get(0);
               Intent intent = new Intent(this.context,signup.class);
               intent.putExtra("fname",you.get(0));
               intent.putExtra("lname",you.get(1));
               intent.putExtra("lvl",you.get(2));
               intent.putExtra("mat",you.get(3));
               intent.putExtra("email",you.get(4));
               intent.putExtra("pass",you.get(5));
               Log.e("POST EXEC", you.get(3));
               //  signup su = new signup();
               //su.setfillinfo(firstname);
               context.startActivity(intent);
           }else{
               Toast.makeText(context,"Invalid Matric Number.",Toast.LENGTH_SHORT).show();
           }




        }else if(counter ==4){
            Toast.makeText(context,s,Toast.LENGTH_LONG).show();
        }else if(counter ==5){
            Toast.makeText(context,s,Toast.LENGTH_LONG).show();
            resultView r = new resultView();
            r.parse(s);
        }

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];String result = "";
        if(type.equalsIgnoreCase("login")){
            counter =1;
            String username = strings[1];
            String password = strings[2];
            try {
                URL url = new URL("https://rottenegg.000webhostapp.com/login.php");
                HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStream output = urlConnection.getOutputStream();
                BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(output , "UTF-8"));
                String postdata = URLEncoder.encode("user_name" , "UTF-8")+ "="+URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("pass_word" , "UTF-8")+ "="+URLEncoder.encode(password,"UTF-8");
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
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(type.equalsIgnoreCase("register")){
            counter = 2;
            String fname = strings[1];
            String lname = strings[2];
            String matric = strings[3];
            String email = strings[4];
            String level = strings[5];
            String password = strings[6];

            try {
                URL url = new URL("https://rottenegg.000webhostapp.com/register.php");
                HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStream output = urlConnection.getOutputStream();
                BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(output , "UTF-8"));
                String postdata = URLEncoder.encode("fname" , "UTF-8")+ "="+URLEncoder.encode(fname,"UTF-8")+"&"+
                        URLEncoder.encode("lname" , "UTF-8")+ "="+URLEncoder.encode(lname,"UTF-8")+"&"+
                        URLEncoder.encode("level" , "UTF-8")+ "="+URLEncoder.encode(level,"UTF-8")+"&"+
                URLEncoder.encode("email" , "UTF-8")+ "="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("matric" , "UTF-8")+ "="+URLEncoder.encode(matric,"UTF-8")+"&"+
                        URLEncoder.encode("pass_word" , "UTF-8")+ "="+URLEncoder.encode(password,"UTF-8");
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
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(type.equalsIgnoreCase("fetch")){
            counter =3;
            String username = strings[1];
            try {
                URL url = new URL("https://rottenegg.000webhostapp.com/fetch.php");
                HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStream output = urlConnection.getOutputStream();
                BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(output , "UTF-8"));
                String postdata = URLEncoder.encode("user_name" , "UTF-8")+ "="+URLEncoder.encode(username,"UTF-8");
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
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(type.equalsIgnoreCase("uploadresult")){
            counter =4;
            String bytes = strings[1];
            String title = strings[2];
            String Level = strings[3];
            Log.e("new",title);

            try {
                //resulttable
                URL url = new URL("https://rottenegg.000webhostapp.com/resulttable.php");
                HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStream output = urlConnection.getOutputStream();
                BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(output , "UTF-8"));
                String postdata = URLEncoder.encode("bitmap" , "UTF-8")+ "="+URLEncoder.encode(bytes,"UTF-8")+"&"+
                        URLEncoder.encode("label" , "UTF-8")+ "="+URLEncoder.encode(title,"UTF-8")+"&"+
                        URLEncoder.encode("level" , "UTF-8")+ "="+URLEncoder.encode(Level,"UTF-8");
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
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(type.equalsIgnoreCase("fetchresult")){
            counter =5;
            String bytes = "gtg";

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
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }








        return null;
    }
}
