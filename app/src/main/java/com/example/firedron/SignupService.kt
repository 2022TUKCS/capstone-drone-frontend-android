package com.example.firedron
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SignupService {

    @FormUrlEncoded
    @POST("auth/users/")
    fun requestSignup(   //input 정의
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("email") email:String
    ) : Call<Auth> //output 정의
}