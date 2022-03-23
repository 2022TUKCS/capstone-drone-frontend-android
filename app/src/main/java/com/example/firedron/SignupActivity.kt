package com.example.firedron

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.firedron.databinding.ActivitySignupBinding
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val signupService = retrofit.create(SignupService::class.java)
        binding.signupButton2.setOnClickListener {
            val emailText = binding.editTextEmail.text.toString()
            val pwordText = binding.editTextUserPassword.text.toString()
            val unameText = binding.editTextUsername.text.toString()

            signupService.requestSignup(unameText,pwordText,emailText).enqueue(object : Callback<Auth> {
                override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                    //웹통신 성공했을때 실행
                    var login = response.body() //코드,메세지
                    Log.d("SignupSuccess", response.toString())
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<Auth>, t: Throwable) {
                    //웹통신 실패시 실행
                    var dialog = AlertDialog.Builder(this@SignupActivity)
                    Log.e("comerror", t.message.toString())
                    dialog.setTitle("실패")
                    dialog.setMessage("통신에 실패했습니다.")
                    dialog.show()
                }


            })

        }

    }
}