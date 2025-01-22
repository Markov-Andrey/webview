package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class MainActivity extends Activity {

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndCreateConfigFile();

        WebView mWebView = findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        String url = readConfigFile();
        if (url != null && !url.isEmpty()) {
            mWebView.loadUrl(url);
        } else {
            Log.e("MainActivity", "URL not found in config file.");
        }
    }

    private void checkAndCreateConfigFile() {
        try {
            FileInputStream fis = openFileInput("url.txt");
            fis.close();
        } catch (IOException e) {
            writeDefaultConfigFile();
        }
    }

    private void writeDefaultConfigFile() {
        try {
            String url_default = "http://192.168.103.222";
            FileOutputStream fos = openFileOutput("url.txt", MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(url_default);
            writer.close();
            Log.i("MainActivity", "Default config file created with URL: " + url_default);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing default config file", e);
        }
    }

    private String readConfigFile() {
        String url = null;
        try {
            FileInputStream fis = openFileInput("url.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            url = reader.readLine();
            reader.close();
        } catch (Exception e) {
            Log.e("MainActivity", "Error reading config file", e);
        }
        return url;
    }
}