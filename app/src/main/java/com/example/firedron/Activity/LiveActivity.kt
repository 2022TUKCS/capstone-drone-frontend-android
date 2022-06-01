package com.example.firedron.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.firedron.R


class LiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live)

        // 추가
        var myWebView: WebView = findViewById(R.id.Webview2)
        myWebView.webViewClient = WebViewClient()
        myWebView.loadUrl("https://www.google.com/") //url 넣는곳
    }
}