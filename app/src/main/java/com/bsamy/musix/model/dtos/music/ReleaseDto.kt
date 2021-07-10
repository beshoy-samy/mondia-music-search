package com.bsamy.musix.model.dtos.music


import com.google.gson.annotations.SerializedName

data class ReleaseDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?
)