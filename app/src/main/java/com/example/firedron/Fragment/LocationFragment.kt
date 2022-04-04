package com.example.firedron.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firedron.Activity.LocationActivity
import com.example.firedron.R
import com.example.firedron.databinding.FragmentLocationBinding

class LocationFragment (
    private val locationActivity: LocationActivity? = null
): Fragment() {
    //    private lateinit var locationSource: FusedLocationSource
//    private lateinit var naverMap: NaverMap //NaverMap
//    private lateinit var mapView: MapView   //xml map 객체
//    private var path = PathOverlay()
    private lateinit var binding: FragmentLocationBinding

//    private var mainActivity: Activity = MainActivity()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLocationBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        mapView = view.findViewById(R.id.naverMap)
//        mapView.getMapAsync(this)
//        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
//
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        Log.d("permissionCheck", "onRequestPermissionsResult()")
//
//        if (locationSource.onRequestPermissionsResult(
//                requestCode, permissions,
//                grantResults
//            )
//        ) {
//            if (!locationSource.isActivated) { // 권한 거부됨
//                // 실행을 막는 코드 필요
//                //Toast.makeText(runningActivity, "앱 실행을 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
//                naverMap.locationTrackingMode = LocationTrackingMode.None
//            }
//            return
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//    // NaverMap 인스턴스가 준비되면 호출되는 콜백 메서드.
//    override fun onMapReady(naverMap: NaverMap) {
//        Log.d("Map22", "MapFragment onMapReady()")
//
//        this.naverMap = naverMap
//
//        //지도 위치를 현 위치로 설정
//        naverMap.locationSource = locationSource
//
//
////        naverMap.minZoom = 18.0
////        naverMap.maxZoom = 18.0
//
//        // 위치 추적 활성화
//        naverMap.locationTrackingMode = LocationTrackingMode.Follow
//
//        Log.d("Map22", "MapFragment runningActivity : $runningActivity")
//
//        if (runningActivity != null) {
//            Log.d("Map22", "${runningActivity.mService}")
//            runningActivity.mService.setNaverMap(naverMap, path)
//        } else recordDetailActivity?.setMap(naverMap, path)
//    }
//
//    // MapView 의 라이프 사이클 메서드 호출
//    override fun onStart() {
//        Log.d("service333", "onStart()")
//        super.onStart()
//        mapView.onStart()
//    }
//
//    override fun onResume() {
//        Log.d("service333", "onResume()")
//        super.onResume()
//        mapView.onResume()
//    }
//
//    override fun onPause() {
//        Log.d("service333", "onPause()")
//        super.onPause()
//        mapView.onPause()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        Log.d("service333", "onSaveInstanceState()")
//        super.onSaveInstanceState(outState)
//        //mapView.onSaveInstanceState(outState)
//    }
//
//    override fun onStop() {
//        Log.d("service333", "onStop()")
//        super.onStop()
//        mapView.onStop()
//    }
//
//    override fun onDestroyView() {
//        Log.d("service333", "onDestroyView()")
//        super.onDestroyView()
//        mapView.onDestroy()
//    }
//
//    override fun onLowMemory() {
//        Log.d("service333", "onLowMemory()")
//        super.onLowMemory()
//        mapView.onLowMemory()
//    }

//    companion object {
//        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
//    }
}