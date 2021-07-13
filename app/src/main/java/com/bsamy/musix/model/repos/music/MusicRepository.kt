package com.bsamy.musix.model.repos.music

import com.bsamy.musix.model.Network
import com.bsamy.musix.model.RequestType
import com.bsamy.musix.model.dtos.music.MusicDto
import com.bsamy.musix.model.network
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val musicRepository: MusicRepository by lazy { MusicRepositoryImp() }

interface MusicRepository {

    suspend fun searchForMusic(
        query: String,
        limit: Int = 20,
        userToken: String
    ): Flow<List<MusicDto>>
}

class MusicRepositoryImp(private val networkConnection: Network = network) :
    MusicRepository {

    override suspend fun searchForMusic(
        query: String,
        limit: Int,
        userToken: String
    ): Flow<List<MusicDto>> =
        flow {
            val listOfMusicType = object : TypeToken<List<MusicDto>>() {}.type
            val result =
                networkConnection.fetch<List<MusicDto>>(
                    url = "v2/api/sayt/flat",
                    RequestType.GET,
                    mapOf("Authorization" to "Bearer $userToken"),
                    mapOf("query" to query, "limit" to limit),
                    returnType = listOfMusicType
                )
            emit(result)
        }

}