package com.bsamy.musix.model.dtos.music


import com.google.gson.annotations.SerializedName

data class ArtistDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("role")
    val role: String?
)