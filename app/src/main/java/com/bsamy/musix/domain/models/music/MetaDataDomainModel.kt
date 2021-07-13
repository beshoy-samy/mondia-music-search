package com.bsamy.musix.domain.models.music

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetaDataDomainModel(
    val originalSongId: String,
    val originalTitle: String,
    val releaseYear: String,
    val genresHierarchy: List<String>,
    val moods: List<String>,
    val tempos: List<String>,
    val languages: String
) : Parcelable
