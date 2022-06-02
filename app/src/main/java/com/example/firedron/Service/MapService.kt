package com.example.firedron.Service
import com.example.firedron.dto.*
import com.naver.maps.geometry.LatLng
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface MapService {
    @FormUrlEncoded
    @PUT("api/v1/flights/{flight_id}/")
    fun requestMap(   //input 정의
        @Path(value = "flight_id", encoded = true) fid: String,
        @Field("flight_path") flight_path: JSONArray,
    ) : Call<FlightPath> //output 정의

    @GET("api/v1/flights/")
    fun responseMap(
    ) : Call<MResponse>
}

interface FlyService {
    @GET("drone/detect")
    fun requestFlight(
    ) : Call<String>
}



