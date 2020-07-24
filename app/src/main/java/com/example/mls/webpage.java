package com.example.mls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class webpage extends AppCompatActivity {
    WebView webView;
    String url;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);

        Intent i = getIntent();setTitle(i.getStringExtra("title"));
        url = i.getStringExtra("url");
        webView = findViewById(R.id.webview);
        progress = findViewById(R.id.pro);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progress.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.GONE);
                super.onPageFinished(view, url);

            }
        });

if(url !=null && !url.equalsIgnoreCase("")){
    webView.loadUrl(url);
}



        WebSettings webSettings = webView.getSettings();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }

    }
}
