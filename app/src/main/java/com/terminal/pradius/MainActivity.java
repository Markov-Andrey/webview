package com.terminal.pradius;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
    private WebView mWebView;
    private LinearLayout loadingPlaceholder;
    private ProgressBar progressBar;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileManager fileManager = new FileManager(this);
        fileManager.writeFile();

        loadingPlaceholder = findViewById(R.id.loading_placeholder);
        progressBar = findViewById(R.id.progress_bar);
        mWebView = findViewById(R.id.activity_main_webview);

        setupWebView(fileManager.readFile());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView(String url) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                loadingPlaceholder.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                handleWebViewError();
            }
        });

        try {
            mWebView.loadUrl(url);
        } catch (Exception e) {
            handleWebViewError();
        }
    }

    private void handleWebViewError() {
        loadingPlaceholder.setVisibility(View.GONE);
        mWebView.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this, "Ошибка! Сервис недоступен.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
