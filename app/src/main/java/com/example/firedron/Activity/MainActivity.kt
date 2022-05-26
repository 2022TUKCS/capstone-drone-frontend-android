package com.example.firedron.Activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.firedron.R
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.firedron.Service.UserInformation
import com.example.firedron.dto.Token
import com.example.firedron.databinding.ActivityMainBinding
import com.example.firedron.dto.Auth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_toolbar.*


import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var temp: ArrayList<Drawable>

    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 500 //delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 3000 // time in milliseconds between successive task executions.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        getUserInformation.requestUser().enqueue(object : Callback<Auth> {
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
        }) //username 로그인한 사용자의 id로 변경
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.location.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("TOKEN", token)
            startActivity(intent)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webview.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("TOKEN", token)
            startActivity(intent)
        }

        var a = adapter(this)
        var pager = findViewById<ViewPager>(R.id.view_pager)
        pager.setAdapter(a)

        var tabLayout = findViewById<TabLayout>(R.id.tablayout)
        tabLayout.setupWithViewPager(pager, true)

        val handler = Handler()
        val Update = Runnable {
            if (currentPage == 4) {
                currentPage = 0
            }
            pager.setCurrentItem(currentPage++, true)
        }

        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, DELAY_MS, PERIOD_MS)

        setSupportActionBar(main_layout_toolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게



//        binding.imageLive.setOnClickListener{
//            val intent = Intent(this, LiveActivity::class.java) //this == MainActivity
//            startActivity(intent)
//        }


//    val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment
//        //네비게이션 컨트롤러
//        val navController = navHostFragment.navController
//        //바텀 네이베이견 뷰와 네비게이션을 묶어준다
//        NavigationUI.setupWithNavController(binding.myBottomView, navController)
        webview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }

        webview.loadUrl("https://08c8-125-190-106-5.jp.ngrok.io/drone/detect")

    }

    override fun onBackPressed() {
        if (webview.canGoBack())
        {
            webview.goBack()
        }
        else
        {
            finish()
        }
    }

    class adapter(var context: Context) : PagerAdapter() {


        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            var view: View? = null;
            var inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.pager_adapter, container, false)
            var imageView = view.findViewById<ImageView>(R.id.imageView)
            var textView: TextView = view.findViewById(R.id.txt)
            // var button : Button = view.findViewById(R.id.imageButton)

            if (position == 0) {
                var gif_id = view.findViewById<ImageView>(R.id.gif_id)
                Glide.with(context).asGif().load(R.raw.drone).into(gif_id)
                imageView.setBackgroundColor((Color.parseColor("#70E5E5E5")))
                textView.text = "실시간영상 보기"
//            button.setOnClickListener {
//                val intent = Intent(context, MapActivity::class.java)
//            }
            } else if (position == 1) {
                var gif_id = view.findViewById<ImageView>(R.id.gif_id)
                Glide.with(context).asGif().load(R.raw.factory).into(gif_id)
                imageView.setBackgroundColor((Color.parseColor("#70E5E5E5")))
                textView.text = "드론경로 설정하기"
            } else if (position == 2) {
                var gif_id = view.findViewById<ImageView>(R.id.gif_id)
                Glide.with(context).asGif().load(R.raw.forest).into(gif_id)
                imageView.setBackgroundColor((Color.parseColor("#70E5E5E5")))
                textView.text = "현재 드론위치 보기"
            } else {
                var gif_id = view.findViewById<ImageView>(R.id.gif_id)
                Glide.with(context).asGif().load(R.raw.city).into(gif_id)
                imageView.setBackgroundColor((Color.parseColor("#70E5E5E5")))
                textView.text = "내 정보 수정하기"
            }
            container.addView(view)
            return view
        }



        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return 4
        }

    }
}
