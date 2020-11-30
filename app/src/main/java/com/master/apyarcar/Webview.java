package com.master.apyarcar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

public class Webview extends AppCompatActivity {

    private InterstitialAd interstitialAd;



    //String link;
    WebView mWebView;


    ProgressBar progressBar;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);



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







        progressBar=findViewById(R.id.progressBar2);
        mWebView=findViewById(R.id.web);

//        adView=findViewById(R.id.adView);
//        AdRequest adRequest=new AdRequest.Builder().build();
//        adView.loadAd(adRequest);


        progressBar.setVisibility(View.VISIBLE);
        mWebView.setWebViewClient(new Browser_home());
        mWebView.setWebChromeClient(new MyChrome());
        WebSettings webSettings=mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true
        );
        webSettings.setAppCacheEnabled(true);
        loadWebsite();

    }
    private void loadWebsite(){
        ConnectivityManager cm=(ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnectedOrConnecting()){
            //   mWebView.loadUrl("https://www.google.com");
            //  mWebView.loadUrl(link);
//            mWebView.loadUrl("" +
//                    "" +
//                    "");


        }else {
            mWebView.setVisibility(View.GONE);
        }

    }

    class Browser_home extends WebViewClient {
        Browser_home(){

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            setTitle(view.getTitle());
            progressBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }
    }


    private class MyChrome extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout frameLayout;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {
        }


        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }



    }
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();

            // MobileAds.initialize(this,"ca-app-pub-8080535183485818~2097022097");
            interstitialAd.loadAd(new AdRequest.Builder().build());
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded(){
                    interstitialAd.show();
                }});


        }else {


                AlertDialog.Builder builder=new AlertDialog.Builder(Webview.this);
                builder.create();
                builder.setTitle("Feedback Alert")
                        .setMessage("Any Feedback?Please Tell us.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {





//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+update));
//                                startActivity(intent);
                                Intent intent=new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        })
                        .setCancelable(false)
                        .setNeutralButton("Cancle",null)

                        .show();
                return;
                //  super.onBackPressed();



          //  super.onBackPressed();
        }

    }


    private void showerror(String message){
        new AlertDialog.Builder(this).setMessage(message).show();

    }

    public void handleResonse(JSONObject respone){
        try {
            boolean status=respone.getBoolean("status");
            if(status==true){

                //  mWebView.loadUrl(link);
                String banner=respone.getString("banner");
                String appId=respone.getString("appId");
                String inter=respone.getString("interstial");
                String uri=respone.getString("linkone");


                AdView adView=new AdView(this);
                adView.setAdUnitId(banner);
                adView.setAdSize(AdSize.BANNER);
                AdRequest adRequest=new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
                if(adView.getAdSize()!=null||adView.getAdUnitId()!=null){
                    adView.loadAd(adRequest);
                    ((LinearLayout)findViewById(R.id.adView)).addView(adView);

                }
                interstitialAd = new InterstitialAd(this);
                // interstitialAd.setAdUnitId("ca-app-pub-8080535183485818/9981005681");
                MobileAds.initialize(this,appId);

                interstitialAd.setAdUnitId(inter);





              //  String uri=getIntent().getStringExtra("linkone");
                System.out.println(uri);
                mWebView.loadUrl(uri);
            }else {
                String title=respone.getString("title");
                String about=respone.getString("about");



              final String  update=respone.getString("update");
                final boolean main=respone.getBoolean("main");





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
    }


}