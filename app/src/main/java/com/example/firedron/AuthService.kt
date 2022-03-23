package com.example.firedron
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("/")
    fun requestAuth(   //input 정의
        @Field("username") username:String,
        @Field("password") password:String
    ) : Call<Auth> //output 정의
}