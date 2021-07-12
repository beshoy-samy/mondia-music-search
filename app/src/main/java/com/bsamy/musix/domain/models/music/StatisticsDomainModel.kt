package com.bsamy.musix.domain.models.music

data class StatisticsDomainModel(
    val estimatedRecentCount: Int,
    val estimatedTotalCount: Int,
    val popularity: Float,
)
