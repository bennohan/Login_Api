package com.example.loginapi.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("mfriend_login")
    suspend fun login(
        @Field("phone") phone: String?,
        @Field("password") password: String?
    ): String

    @FormUrlEncoded
    @POST("mfriend_register")
    suspend fun register(
        @Field("phone") phone: String?,
        @Field("password") password: String?,
        @Field("name") name: String?
    ) : String

}
