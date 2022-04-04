package com.example.firedron.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.firedron.Token
import com.example.firedron.databinding.ActivityMainBinding

import okhttp3.OkHttpClient
import okhttp3.Request


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        } //하단바, 상단바 모두 숨김모드

        val intent: Intent = getIntent()
        val token = intent.getParcelableExtra<Token>("TOKEN")
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Token $token")
                .build()
            chain.proceed(newRequest)
        }.build()

        Log.d("GOTIT", token.toString())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.location.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
     
        binding.imageLive.setOnClickListener{
            val intent = Intent(this, LiveActivity::class.java) //this == MainActivity
            startActivity(intent)
        }

    }
}