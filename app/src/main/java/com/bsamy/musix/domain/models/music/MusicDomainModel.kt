package com.bsamy.musix.domain.models.music

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicDomainModel(
    val id: Int,
    val smallCover: String?,
    val bigCover: String?,
    val title: String,
    val releaseTitle: String,
    val artist: ArtistDomainModel,
    val additionalArtists: List<ArtistDomainModel>,
    val type: MusicType,
    val publishingDate: String,
    val volumeNumber: Int,
    val trackNumber: Int,
    val duration: Int,
    val genres: List<String>,
    val statistics: StatisticsDomainModel,
    val metaData: MetaDataDomainModel
) : Parcelable
