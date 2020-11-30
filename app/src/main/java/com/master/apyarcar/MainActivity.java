package com.master.apyarcar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "https://raw.githubusercontent.com/phoopyone/dar.json/main/README.md";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        handleResonse(response);

                        //  textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showerror(error.getMessage());
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        Volley.newRequestQueue(this).add(jsonObjectRequest);


    }
    private void showerror(String message){
        new AlertDialog.Builder(this).setMessage(message).show();

    }
    public void handleResonse(JSONObject respone){
        try {
            boolean status=respone.getBoolean("status");

            if(status==true){
                int version=respone.getInt("version");

                if(version==1){
                    Intent intent=new Intent(this,Password.class);


                    startActivity(intent);
                }else {

                    String title=respone.getString("title");
                    String about=respone.getString("about");



                    final String update=respone.getString("update");






                    final AlertDialog.Builder builder= new AlertDialog.Builder(this).
                            setTitle(title)
                            .setMessage(about);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //  if(main){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.master.massagevideos"));
                            startActivity(intent);
//                           }else {
//                               finish();
//
//                           }


                        }
                    });
                    builder.show();

                }


                // String link=respone.getString("link");












            }else {
                // final String linkone=respone.getString("linkone");
//                Intent i=new Intent(MainActivity.this,FbWebview.class);
//                i.putExtra("linkone",linkone);
//                startActivity(i);

                String title=respone.getString("title");
                String about=respone.getString("about");



                final String update=respone.getString("update");






                final AlertDialog.Builder builder= new AlertDialog.Builder(this).
                        setTitle(title)
                        .setMessage(about);
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //  if(main){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+update));
                        startActivity(intent);
//                           }else {
//                               finish();
//
//                           }


                    }
                });
                builder.show();



            }
        } catch (JSONException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }}
