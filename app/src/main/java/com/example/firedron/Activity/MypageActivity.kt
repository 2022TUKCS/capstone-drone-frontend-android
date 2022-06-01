package com.example.firedron.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.firedron.R
import com.example.firedron.Service.UserInformation
import com.example.firedron.databinding.ActivityMainBinding
import com.example.firedron.databinding.ActivityMypageBinding
import com.example.firedron.dto.Auth
import com.example.firedron.dto.Token
import kotlinx.android.synthetic.main.activity_mypage.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        } //하단바, 상단바 모두 숨김모드
        lateinit var binding: ActivityMypageBinding
        lateinit var uid: String
        val intent: Intent = getIntent()
        val token = intent.getParcelableExtra<Token>("TOKEN")
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Token ${token?.auth_token}")
                .build()
            chain.proceed(newRequest)
        }.build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://"+getString(R.string.AWS)+":8000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val getUserInformation = retrofit.create(UserInformation::class.java)
        getUserInformation.requestUser().enqueue(object: Callback<Auth> {
            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                if (response.code() == 200) {
                    val user = response.body()
                    Log.w("GET", user.toString())
                    username2.text = user?.username.toString()
                    uid = user?.id.toString()
                } else {
                    Log.w("RESPONSEERROR", response.toString())
                    Log.w("RESPONSEERROR", response.body().toString())
                }
            }

            override fun onFailure(call: Call<Auth>, t: Throwable) {
                Log.d("FAILED", t.message.toString())
            }
        }) //username 로그인한 사용자의 id로 변경

        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addDrone.setOnClickListener {
            Log.d("LOCATION", "CLICKED")
            val intent = Intent(this, DroneActivity::class.java)
            intent.putExtra("TOKEN", token)
            intent.putExtra("UID", uid)
            startActivity(intent)
        }
    }
}