package com.example.firedron.Service
import com.example.firedron.dto.Auth
import com.example.firedron.dto.UToken
import retrofit2.Call
import retrofit2.http.*

interface SignupService {
    @FormUrlEncoded
    @POST("auth/users/")
    fun requestSignup(   //input 정의
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("email") email:String,
    ) : Call<Auth> //output 정의

    @FormUrlEncoded
    @PUT("api/v1/user/{uid}/")
    fun putUserFCMToken(
        @Path(value = "uid", encoded = true) uid: String,
        @Field("token") token: String,
    ) : Call<UToken>
}
