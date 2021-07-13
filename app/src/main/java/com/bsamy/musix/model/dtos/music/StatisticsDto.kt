package com.bsamy.musix.model.dtos.music


import com.google.gson.annotations.SerializedName

data class StatisticsDto(
    @SerializedName("estimatedRecentCount")
    val estimatedRecentCount: Int?,
    @SerializedName("estimatedTotalCount")
    val estimatedTotalCount: Int?,
    @SerializedName("popularity")
    val popularity: Float?
)