package com.pradius.terminal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    private WebView mWebView;
    private LinearLayout loadingPlaceholder;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileManager fileManager = new FileManager(this);
        fileManager.writeFile();
        loadingPlaceholder = findViewById(R.id.loading_placeholder);
        mWebView = findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                loadingPlaceholder.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);
            }
        });

        String url = fileManager.readFile();
        mWebView.loadUrl(url);
    }
}
