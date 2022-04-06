package com.example.firedron.dto

import android.os.Parcelable
import com.naver.maps.geometry.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Map(
    var flight_record_url: String,
    var auto_start_time: String,
    var auto_end_time: String,
//    var flight_path: MutableList<LatLng>
    var flight_path: String,
    var flight_record: String?

) : Parcelable
