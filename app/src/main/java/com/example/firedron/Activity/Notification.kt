package com.example.firedron.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.example.firedron.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource

class Notification :  Activity(), OnMapReadyCallback {
    private lateinit var mapView: com.naver.maps.map.MapView
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val token = intent.extras!!.getString("token")
        val type = intent.extras!!.getString("type")
        val lat = intent.extras!!.getString("lat")
        val lng = intent.extras!!.getString("lng")
        val time = intent.extras!!.getString("time")
        mapView = findViewById(R.id.map_view_notif)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
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


    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        val token = intent.extras!!.getString("token")
        val type = intent.extras!!.getString("type")
        val lat = intent.extras!!.getString("lat")
        val lng = intent.extras!!.getString("lng")
        val time = intent.extras!!.getString("time")
        val marker_place = LatLng(lat?.toDouble() ?: 0.0, lng?.toDouble() ?: 0.0)
        val marker = Marker()
        Log.d("LAT", lat.toString())
        marker.position = marker_place
        marker.map = naverMap
        marker.iconTintColor = Color.RED
        marker.captionText = type+"\n"+time+"\n"+marker_place.toString()
        marker.icon = OverlayImage.fromResource(R.drawable.fire)
        marker.captionTextSize = 15F
        marker.captionColor = Color.RED
        marker.setOnClickListener {
            val intent = Intent(this, LiveActivity::class.java)
            startActivity(intent)
            true
        }
    }
}