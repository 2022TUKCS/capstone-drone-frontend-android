package com.example.firedron.Service
import com.example.firedron.dto.Auth
import com.example.firedron.dto.Token
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserInformation {
    @GET("auth/users/me/")
    fun requestUser(   //input 정의
    ) : Call<Auth> //output 정의
}