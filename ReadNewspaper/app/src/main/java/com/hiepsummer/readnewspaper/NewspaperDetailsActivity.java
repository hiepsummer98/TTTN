package com.hiepsummer.readnewspaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NewspaperDetailsActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper_details);

        webView = findViewById(R.id.webViewTinTuc);
        Intent intent = getIntent();
        String link = intent.getStringExtra("linktintuc");
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());

    }
}
