package com.example.firedron.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firedron.R
import com.example.firedron.Service.LoginService
import com.example.firedron.dto.Auth
import com.example.firedron.Service.SignupService
import com.example.firedron.databinding.ActivitySignupBinding
import com.example.firedron.dto.Token
import com.example.firedron.dto.UToken
import com.google.firebase.messaging.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.OkHttpClient
import okhttp3.Request

class SignupActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://"+getString(R.string.AWS)+":8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val loginservice = retrofit.create(LoginService::class.java)

        val signupService = retrofit.create(SignupService::class.java)
        binding.signupButton2.setOnClickListener {
            val emailText = binding.editTextEmail.text.toString()
            val pwordText = binding.editTextUserPassword.text.toString()
            val unameText = binding.editTextUsername.text.toString()

            signupService.requestSignup(unameText,pwordText,emailText).enqueue(object : Callback<Auth> {
                override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                    //웹통신 성공했을때 실행
                    Log.d("SignupSuccess", response.toString())
                    if (response.code() == 201){
                        val uid = response.body()?.id.toString()
                        loginservice.requestLogin(unameText,pwordText).enqueue(object :Callback<Token>{
                            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                                //웹통신 성공했을때 실행
                                Log.d("SignInSuccess", response.toString())
                                if (response?.code() == 200) {
                                    val loginResponse = response.body()
                                    Log.d("TOKEN", loginResponse.toString())
                                    val token = loginResponse?.auth_token

                                    val client = OkHttpClient.Builder().addInterceptor { chain ->
                                        val newRequest: Request = chain.request().newBuilder()
                                            .header("Authorization", "Token $token")
                                            .build()
                                        chain.proceed(newRequest)
                                    }.build()
                                    val tokenRetrofit = Retrofit.Builder()
                                        .baseUrl("http://"+getString(R.string.AWS)+":8000/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(client)
                                        .build()
                                    val signupService = tokenRetrofit.create(SignupService::class.java)
                                    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                                        if (!task.isSuccessful) {
                                            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                                            return@OnCompleteListener
                                        }
                                        val appToken = task.result.toString()

                                        signupService.putUserFCMToken(uid, appToken).enqueue(object : Callback<UToken> {
                                            override fun onResponse(
                                                call: Call<UToken>,
                                                response: Response<UToken>
                                            ) {
                                                if (response.code() == 200) {
                                                    Log.d("Token", "Registration Complete")
                                                } else {
                                                    Log.d("Token", response.errorBody().toString())
                                                }
                                            }
                                            override fun onFailure(call: Call<UToken>, t: Throwable) {
                                                Log.d("Token", "Registration Failed")
                                            }
                                        })
                                        // Get new FCM registration token
                                        Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                                    })

                                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                                    intent.putExtra("TOKEN", Token(token))
                                    startActivity(intent)
                                }

                                else { // 로그인 실패 시
                                    Log.e("LoginError", "${response?.code()} ${response?.message()}")
                                }

                            }

                            override fun onFailure(call: retrofit2.Call<Token>, t: Throwable) {
                                //웹통신 실패시 실행
                                var dialog = AlertDialog.Builder(this@SignupActivity)
                                Log.e("comerror", t.message.toString())
                                dialog.setTitle("실패")
                                dialog.setMessage("통신에 실패했습니다.")
                                dialog.show()
                            }

                        })

                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this@SignupActivity, "아이디 또는 비밀번호가 너무 짧습니다.\n" +
                                "입력하신 내용을 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
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