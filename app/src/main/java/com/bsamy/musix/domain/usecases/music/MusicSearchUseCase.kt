package com.bsamy.musix.domain.usecases.music

import com.bsamy.musix.domain.models.music.ArtistDomainModel
import com.bsamy.musix.domain.models.music.MetaDataDomainModel
import com.bsamy.musix.domain.models.music.MusicDomainModel
import com.bsamy.musix.domain.models.music.StatisticsDomainModel
import com.bsamy.musix.model.UserToken
import com.bsamy.musix.model.dtos.music.ArtistDto
import com.bsamy.musix.model.dtos.music.MusicDto
import com.bsamy.musix.model.repos.music.MusicRepository
import com.bsamy.musix.model.repos.music.musicRepository
import com.bsamy.musix.utils.orNotFound
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val musicSearchUseCase: MusicSearchUseCase by lazy { MusicSearchUseCaseImp() }

interface MusicSearchUseCase {

    suspend fun searchForMusic(query: String, token: String): Flow<List<MusicDomainModel>>

}

private class MusicSearchUseCaseImp(private val musicRepo: MusicRepository = musicRepository) :
    MusicSearchUseCase {

    override suspend fun searchForMusic(query: String, token: String) =
        musicRepo.searchForMusic(query, userToken = token).map { it.mapToDomainModel() }

}

private fun List<MusicDto>.mapToDomainModel() =
    map { musicDto ->
        MusicDomainModel(
            musicDto.id.orNotFound(),
            musicDto.cover?.medium,
            musicDto.title.orEmpty(),
            musicDto.release?.title.orEmpty(),
            musicDto.artist.mapToDomainModel(),
            musicDto.additionalArtists.orEmpty().map { it.mapToDomainModel() },
            musicDto.type.orEmpty(),
            musicDto.publishingDate.orEmpty(),
            musicDto.volumeNumber.orNotFound(),
            musicDto.trackNumber.orNotFound(),
            musicDto.duration.orNotFound(),
            musicDto.genres.orEmpty(),
            StatisticsDomainModel(
                musicDto.statistics?.estimatedRecentCount.orNotFound(),
                musicDto.statistics?.estimatedTotalCount.orNotFound(),
                musicDto.statistics?.popularity.orNotFound()
            ),
            MetaDataDomainModel(
                musicDto.extendedMetaData?.originalSongId.orEmpty(),
                musicDto.extendedMetaData?.originalTitle.orEmpty(),
                musicDto.extendedMetaData?.releaseYear.orEmpty(),
                musicDto.extendedMetaData?.genresHierarchy.orEmpty(),
                musicDto.extendedMetaData?.moods.orEmpty(),
                musicDto.extendedMetaData?.tempos.orEmpty(),
                musicDto.extendedMetaData?.languages.orEmpty()
            )
        )
    }

private fun ArtistDto?.mapToDomainModel() =
    ArtistDomainModel(
        this?.id.orNotFound(),
        this?.name.orEmpty(),
        this?.role.orEmpty()
    )