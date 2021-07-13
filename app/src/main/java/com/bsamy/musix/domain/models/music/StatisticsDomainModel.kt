package com.bsamy.musix.domain.models.music

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticsDomainModel(
    val estimatedRecentCount: Int,
    val estimatedTotalCount: Int,
    val popularity: Float,
) : Parcelable
