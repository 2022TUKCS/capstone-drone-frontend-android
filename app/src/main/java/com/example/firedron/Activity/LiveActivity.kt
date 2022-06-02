package com.example.firedron.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.firedron.R
import kotlinx.android.synthetic.main.activity_main.*


class LiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live)

        // 추가
        var myWebView: WebView = findViewById(R.id.Webview2)
        myWebView.webViewClient = WebViewClient()
        myWebView.loadUrl("https://9233-118-222-85-227.jp.ngrok.io/drone/detect") //url 넣는곳

        myWebView.apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.builtInZoomControls = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)

        }
    }

}

