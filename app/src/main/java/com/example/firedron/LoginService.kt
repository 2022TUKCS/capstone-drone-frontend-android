package com.example.firedron
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @FormUrlEncoded
    @POST("/app_login/")
    fun requestLogin(   //input 정의
        @Field("username") userid:String,
        @Field("password") userpw:String
    ) : Call<Login> //output 정의
}