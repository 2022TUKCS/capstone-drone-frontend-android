package com.example.firedron

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firedron.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent: Intent = getIntent()
        val token = intent.getParcelableExtra<Token>("TOKEN")
        Log.d("GOTIT", token.toString())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.location.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        binding.editDiary.setOnClickListener {
            val intent = Intent (this,DiaryActivity::class.java)
            startActivity(intent)
        }
        binding.imageLive.setOnClickListener{
            val intent = Intent(this, LiveActivity::class.java) //this == MainActivity
            startActivity(intent)
        }

    }
}