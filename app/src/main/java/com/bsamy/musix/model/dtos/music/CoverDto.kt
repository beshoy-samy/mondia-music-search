package com.bsamy.musix.model.dtos.music


import com.google.gson.annotations.SerializedName

data class CoverDto(
    @SerializedName("large")
    val large: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("small")
    val small: String?,
    @SerializedName("tiny")
    val tiny: String?
)