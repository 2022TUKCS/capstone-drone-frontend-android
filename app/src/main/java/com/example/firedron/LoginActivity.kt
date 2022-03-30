package com.example.firedron

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
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
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

//        val auth_retriever = Retrofit.Builder()
//            .baseUrl("http://localhost:8000/api-token-auth/")
//            .build()

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
                    //var login = response.body() //코드,메세지
                    Log.d("SignInSuccess", response.toString())
                    if (response?.code() == 200) {
                        val loginResponse = response.body()
                        Log.d("LoginResponse", loginResponse?.auth_token.toString())
                        val token = loginResponse?.auth_token
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("TOKEN", Token(token))
                        startActivity(intent)
                    } else { // 로그인 실패 시
                        Log.e("LoginError", "${response?.code()} ${response?.message()}")
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


