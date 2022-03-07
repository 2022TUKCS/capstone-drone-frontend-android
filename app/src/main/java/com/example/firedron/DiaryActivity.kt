package com.example.firedron

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firedron.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}