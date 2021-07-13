package com.bsamy.musix.model.dtos

import com.google.gson.annotations.SerializedName

data class AuthenticationDto(
    @SerializedName("accessToken")
    val accessToken: String? = null,
    @SerializedName("expiresIn")
    val expiresIn: String? = null,
    @SerializedName("tokenType")
    val tokenType: String? = null
)