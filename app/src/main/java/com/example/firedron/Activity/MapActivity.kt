package com.example.firedron.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.firedron.R
import com.example.firedron.Service.MapService
import com.example.firedron.Token
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapActivity : Activity(), OnMapReadyCallback {

    private lateinit var mapView: com.naver.maps.map.MapView
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        } //하단바, 상단바 모두 숨김모드

        val intent: Intent = getIntent()
        val token = intent.getParcelableExtra<Token>("TOKEN")
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Token $token")
                .build()
            chain.proceed(newRequest)
        }.build() // DB 헤더(header) 만들어주는부분

        val retrofit = Retrofit.Builder() //Retrofit 부분
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val MapService = retrofit.create(MapService::class.java)

        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        val marker = Marker()
        val marker2 = Marker()
        val marker3 = Marker()
        val marker4 = Marker()

        naverMap.setOnMapClickListener { point, coord ->
            Toast.makeText(
                this, "${coord.latitude}, ${coord.longitude}",
                Toast.LENGTH_SHORT
            ).show()


                    marker.position = LatLng(coord.latitude, coord.longitude)
                    //한국공대 좌표 : 37.3340, 126.7337
                    marker.map = naverMap
                    marker.iconTintColor = Color.BLUE

            marker2.position = LatLng(coord.latitude, coord.longitude)
            marker2.map = naverMap
            marker2.iconTintColor = Color.RED

            marker3.position = LatLng(coord.latitude, coord.longitude)
            marker3.map = naverMap
            marker3.iconTintColor = Color.GREEN

            marker4.position = LatLng(coord.latitude, coord.longitude)
            marker4.map = naverMap
            marker4.iconTintColor = Color.YELLOW


            //마커도 여기다가 추가하면됨 위치를 알고있어야 가능 아니면 오류뜸
        }
    }



    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
