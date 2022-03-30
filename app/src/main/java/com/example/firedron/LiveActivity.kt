package com.example.firedron

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.firedron.databinding.ActivityLocationBinding

class LiveActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLocationBinding
    private lateinit var transaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        transaction = supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, MapFragment())
//        transaction.commit()

//        binding.button.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
    }
}