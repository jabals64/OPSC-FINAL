package com.example.carspotteropsc7312poe.networking

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/user/signup")
    fun signUpUser(@Body signUpRequest: SignUpRequest): Call<SignUpResponse?>

    @Headers("Content-Type: application/json")
    @POST("/user/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse?>
}
