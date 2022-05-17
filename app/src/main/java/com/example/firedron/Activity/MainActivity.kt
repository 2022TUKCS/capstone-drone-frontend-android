package com.example.firedron.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.firedron.R
import com.example.firedron.Service.MapService
import com.example.firedron.Service.UserInformation
import com.example.firedron.dto.Token
import com.example.firedron.databinding.ActivityMainBinding
import com.example.firedron.dto.Auth
import com.example.firedron.dto.Map
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*


import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.decorView.apply {
//            // Hide both the navigation bar and the status bar.
//            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
//            // a general rule, you should design your app to hide the status bar whenever you
//            // hide the navigation bar.
//            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
//        } //하단바, 상단바 모두 숨김모드

        val intent: Intent = getIntent()
        val token = intent.getParcelableExtra<Token>("TOKEN")
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token.toString()
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Token ${token?.auth_token}")
                .build()
            chain.proceed(newRequest)
        }.build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val getUserInformation = retrofit.create(UserInformation::class.java)
        getUserInformation.requestUser().enqueue(object: Callback<Auth>{
            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                if (response.code() == 200) {
                    val user = response.body()
                    Log.w("GET", user.toString())
                    username.text = user?.username.toString()
                } else {
                    Log.w("RESPONSEERROR", response.toString())
                    Log.w("RESPONSEERROR", response.body().toString())
                }
            }

            override fun onFailure(call: Call<Auth>, t: Throwable) {
                Log.d("FAILED", t.message.toString())
            }
        })
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.location.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("TOKEN", token)
            startActivity(intent)
        }

        binding.imageLive.setOnClickListener{
            val intent = Intent(this, LiveActivity::class.java) //this == MainActivity
            startActivity(intent)
        }


//    val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment
//        //네비게이션 컨트롤러
//        val navController = navHostFragment.navController
//        //바텀 네이베이견 뷰와 네비게이션을 묶어준다
//        NavigationUI.setupWithNavController(binding.myBottomView, navController)
    }
}