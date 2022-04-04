package com.example.firedron.Service
import com.example.firedron.Token
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MapService {

    @FormUrlEncoded
    @POST("api/v1/flights/")
    fun requestLogin(   //input 정의
        @Field("flight_record_url") flight_record_url:String,
        @Field("auto_start_time") auto_start_time:String,
        @Field("auto_end_time") auto_end_time:String,
        @Field("flight_path") flight_path:String,
        @Field("drone_id_id") drone_id_id:String
    ) : Call<Token> //output 정의
}