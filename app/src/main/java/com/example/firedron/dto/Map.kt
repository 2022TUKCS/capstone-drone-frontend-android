package com.example.firedron.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.naver.maps.geometry.LatLng
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray
import org.json.JSONObject

@Parcelize
data class Map(
    var flight_record_url: String,
    var auto_start_time: String,
    var auto_end_time: String,
    var flight_record: String?

) : Parcelable

data class Coordinates(
    @SerializedName("lat")
    var lat: Double?,
    @SerializedName("lng")
    var lng: Double?
)
