package com.bsamy.musix.domain.usecases.music

import com.bsamy.musix.domain.models.music.MusicType
import com.bsamy.musix.model.dtos.music.MusicDto
import com.bsamy.musix.model.repos.music.MusicRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

@FlowPreview
class MusicSearchUseCaseImpTest {

    @Test
    fun `searchForMusic() with query and userToken then emit listOf none artists MusicDomainModel`() =
        runBlocking {
            //arrange
            val repository = object : MusicRepository {

                override suspend fun searchForMusic(
                    query: String,
                    limit: Int,
                    userToken: String
                ) = flowOf(
                    listOf(
                        MusicDto(id = 1, type = MusicType.SONG.key),
                        MusicDto(id = 2, type = MusicType.RELEASE.key),
                        MusicDto(id = 3, type = MusicType.ARTIST.key)
                    )
                )
            }
            val musicSearchUseCase = MusicSearchUseCaseImp(repository)

            //act
            val result = musicSearchUseCase.searchForMusic("Hi", "").first()

            //assert
            assert(result.count { it.type == MusicType.ARTIST } == 0)
        }

    @Test
    fun `searchForMusic() with invalid query and userToken then emit nothing`() =
        runBlocking {
            //arrange
            val repository = object : MusicRepository {

                override suspend fun searchForMusic(
                    query: String,
                    limit: Int,
                    userToken: String
                ) = flowOf(listOf(MusicDto(id = 1, type = MusicType.SONG.key)))
            }
            val musicSearchUseCase = MusicSearchUseCaseImp(repository)

            //act
            val result = musicSearchUseCase.searchForMusic("1", "").toList()

            //assert
            assert(result.isEmpty())
        }

    @Test(expected = Exception::class)
    fun `searchForMusic() then throw exception`() = runBlocking {
        //arrange
        val repository = object : MusicRepository {

            override suspend fun searchForMusic(
                query: String,
                limit: Int,
                userToken: String
            ) = throw Exception()
        }
        val musicSearchUseCase = MusicSearchUseCaseImp(repository)

        //act
        val result = musicSearchUseCase.searchForMusic("Hi", "").first()
    }

}