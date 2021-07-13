package com.bsamy.musix.model.dtos.music


import com.google.gson.annotations.SerializedName

data class MusicDto(
    @SerializedName("additionalArtists")
    val additionalArtists: List<ArtistDto>? = null,
    @SerializedName("adfunded")
    val adfunded: Boolean? = null,
    @SerializedName("bundleOnly")
    val bundleOnly: Boolean? = null,
    @SerializedName("cover")
    val cover: CoverDto? = null,
    @SerializedName("duration")
    val duration: Int? = null,
    @SerializedName("extendedMetaData")
    val extendedMetaData: ExtendedMetaDataDto? = null,
    @SerializedName("genres")
    val genres: List<String>? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("mainArtist")
    val artist: ArtistDto? = null,
    @SerializedName("publishingDate")
    val publishingDate: String? = null,
    @SerializedName("release")
    val release: ReleaseDto? = null,
    @SerializedName("statistics")
    val statistics: StatisticsDto? = null,
    @SerializedName("streamable")
    val streamable: Boolean? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("trackNumber")
    val trackNumber: Int? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("volumeNumber")
    val volumeNumber: Int? = null
)