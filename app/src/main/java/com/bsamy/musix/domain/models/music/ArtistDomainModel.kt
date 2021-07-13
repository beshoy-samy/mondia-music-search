package com.bsamy.musix.domain.models.music

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistDomainModel(
    val id: Int,
    val name: String,
    val role: String
) : Parcelable