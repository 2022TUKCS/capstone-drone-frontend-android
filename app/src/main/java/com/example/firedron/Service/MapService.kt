package com.example.firedron.Service
import com.example.firedron.dto.Coordinates
import com.example.firedron.dto.MResponse
import com.example.firedron.dto.Map
import com.example.firedron.dto.Token
import com.naver.maps.geometry.LatLng
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MapService {
    @FormUrlEncoded
    @POST("api/v1/flights/")
    fun requestMap(   //input 정의
        @Field("flight_record_url") flight_record_url:String,
        @Field("auto_start_time") auto_start_time:String,
        @Field("auto_end_time") auto_end_time:String,
        @Field("flight_path") flight_path: JSONArray,
        @Field("flight_record") flight_record: String?
    ) : Call<Map> //output 정의

    @GET("api/v1/flights/")
    fun responseMap(
    ) : Call<MResponse>
}



