package com.bsamy.musix.model.dtos.music


import com.google.gson.annotations.SerializedName

data class MusicDto(
    @SerializedName("additionalArtists")
    val additionalArtists: List<ArtistDto>?,
    @SerializedName("adfunded")
    val adfunded: Boolean?,
    @SerializedName("bundleOnly")
    val bundleOnly: Boolean?,
    @SerializedName("cover")
    val cover: CoverDto?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("extendedMetaData")
    val extendedMetaData: ExtendedMetaDataDto?,
    @SerializedName("genres")
    val genres: List<String>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("mainArtist")
    val artist: ArtistDto?,
    @SerializedName("publishingDate")
    val publishingDate: String?,
    @SerializedName("release")
    val release: ReleaseDto?,
    @SerializedName("statistics")
    val statistics: StatisticsDto?,
    @SerializedName("streamable")
    val streamable: Boolean?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("trackNumber")
    val trackNumber: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("volumeNumber")
    val volumeNumber: Int?
)