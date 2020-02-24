package com.hiepsummer.docbao.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hiepsummer.docbao.R;

public class DetailsActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // findView
        webView = findViewById(R.id.webViewDetails);

        //get Data from Intent
        Intent intent = getIntent();
        String duonglink = intent.getStringExtra("link");
        webView.loadUrl(duonglink);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().getBuiltInZoomControls();
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAppCacheMaxSize(Long.MAX_VALUE);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );

        //action for Actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nội dung");

    }

    //Backpress in ActionBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
