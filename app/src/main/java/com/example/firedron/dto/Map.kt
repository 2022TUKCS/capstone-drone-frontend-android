package com.example.firedron.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.naver.maps.geometry.LatLng
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Map(
    var flight_record_url: String,
    var auto_start_time: String,
    var auto_end_time: String,
    var flight_record: String?

) : Parcelable

data class FlightPath(
    var id: UUID,
    var flight_path: List<Coordinates>,
    var admin_id: UUID
)

data class RMap(
    var id: UUID,
    var flight_path: List<Coordinates>,
    var admin_id: UUID
)

data class Coordinates(
    @SerializedName("lat")
    var lat: Double,
    @SerializedName("lng")
    var lng: Double
)

data class MResponse(
    var count: String,
    var next: String?,
    var previous: String?,
    var results: List<RMap>
) {
}