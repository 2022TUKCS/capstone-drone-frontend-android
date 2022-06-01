package com.example.firedron.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firedron.R
import com.example.firedron.Service.LoginService
import com.example.firedron.dto.Token
import com.example.firedron.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://"+getString(R.string.AWS)+":8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val loginservice = retrofit.create(LoginService::class.java)

        binding.signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }


        binding.loginButton.setOnClickListener{
            val textName = binding.editTextUserName.text.toString()
            val textPw = binding.editTextPassword.text.toString()

            loginservice.requestLogin(textName,textPw).enqueue(object :Callback<Token>{
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    //웹통신 성공했을때 실행
                    Log.d("SignInSuccess", response.toString())
                    if (response?.code() == 200) {
                        val loginResponse = response.body()
                        Log.d("TOKEN", loginResponse.toString())
                        val token = loginResponse?.auth_token
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("TOKEN", Token(token))
                        startActivity(intent)
                    }

                    else { // 로그인 실패 시
                        Log.e("LoginError", "${response?.code()} ${response?.message()}")
                        Toast.makeText(this@LoginActivity, "아이디 또는 비밀번호를 잘못 입력했습니다.\n" +
                                "입력하신 내용을 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: retrofit2.Call<Token>, t: Throwable) {
                    //웹통신 실패시 실행
                    var dialog = AlertDialog.Builder(this@LoginActivity)
                    Log.e("comerror", t.message.toString())
                    dialog.setTitle("실패")
                    dialog.setMessage("통신에 실패했습니다.")
                    dialog.show()
                }

            })
        }
    }
}


