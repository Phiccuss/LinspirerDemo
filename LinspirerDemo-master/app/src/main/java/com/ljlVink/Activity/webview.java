package com.ljlVink.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.huosoft.wisdomclass.linspirerdemo.R;

public class webview extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
         webView = (WebView) findViewById(R.id.webview);
        String url=getIntent().getStringExtra("url");
        if(url.contains("github")){
            Toast.makeText(this, "github可能要打开较长时间,请稍等...", Toast.LENGTH_SHORT).show();
        }
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        if(keyCode==KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
        }
        return super.onKeyDown(keyCode,keyEvent);

    }
}