package com.bsamy.musix.domain.models.music

data class MusicDomainModel(
    val id: Int,
    val cover: String?,
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
)
