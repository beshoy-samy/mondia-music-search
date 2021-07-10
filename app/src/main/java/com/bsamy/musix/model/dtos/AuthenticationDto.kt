package com.bsamy.musix.model.dtos

import com.google.gson.annotations.SerializedName

data class AuthenticationDto(
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("expiresIn")
    val expiresIn: String?,
    @SerializedName("tokenType")
    val tokenType: String = "Bearer"
)