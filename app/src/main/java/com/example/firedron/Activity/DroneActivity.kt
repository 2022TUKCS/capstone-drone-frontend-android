package com.example.firedron.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firedron.R
import com.example.firedron.Service.DroneService
import com.example.firedron.dto.Drone
import com.example.firedron.dto.Token
import kotlinx.android.synthetic.main.activity_drone.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class DroneActivity : AppCompatActivity() {
    private lateinit var droneAlias: String
    private lateinit var surveillanceArea: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drone)
        val intent: Intent = intent
        val token = intent.getParcelableExtra<Token>("TOKEN")
        val uid = intent.getStringExtra("UID")
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .header("Authorization", "Token ${token?.auth_token}")
                .build()
            chain.proceed(newRequest)
        }.build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val droneService = retrofit.create(DroneService::class.java)
        post_drone_button.setOnClickListener {
            if (uid != null) {
                droneAlias = drone_alias_field.text.toString()
                surveillanceArea = surveillance_area_field.text.toString()
                droneService.sendDroneInformation(droneAlias, surveillanceArea, uid).enqueue(object: Callback<Drone> {
                    override fun onResponse(call: Call<Drone>, response: Response<Drone>) {
                        if(response.isSuccessful) {
                            Log.d("DRONE_NT_SUCCESS", response.isSuccessful.toString())
                            val intent = Intent(this@DroneActivity, MapActivity::class.java)
                            Log.d("DRONE_NT_SUCCESS", response.body()?.flight.toString())
                            intent.putExtra("TOKEN", token)
                            intent.putExtra("FLIGHT", response.body()?.flight)
                            startActivity(intent)
                        } else {
                            Log.d("SENT_DATA", droneAlias+(surveillanceArea)+uid)
                            Log.d("DRONE_NT_ERROR", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Drone>, t: Throwable) {
                        Log.e("DRONE_FAILURE", t.localizedMessage)
                    }
                })
            }
        }
    }

}