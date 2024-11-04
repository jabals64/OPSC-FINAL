package com.example.carspotteropsc7312poe.networking

data class LoginResponse(
    val token: String,
    val userId: String,
    val success: Boolean
)
