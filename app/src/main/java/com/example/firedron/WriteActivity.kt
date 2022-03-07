package com.example.firedron

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firedron.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}