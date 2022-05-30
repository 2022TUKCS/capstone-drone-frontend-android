package com.example.firedron.Service

import com.example.firedron.dto.Drone
import com.example.firedron.dto.Token
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DroneService {
    @FormUrlEncoded
    @POST("api/v1/drones/")
    fun sendDroneInformation(   //input 정의
        @Field("drone_alias") drone_name: String,
        @Field("surveilance_area") surveillance_area:String,
        @Field("admin_id") admin_id: String,
        ) : Call<Drone> //output 정의
}