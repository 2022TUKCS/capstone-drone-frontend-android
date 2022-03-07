package com.example.firedron

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firedron.databinding.ActivityLiveBinding

class FlashActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}