package com.bsamy.musix.model.repos.music

import com.bsamy.musix.model.Network
import com.bsamy.musix.model.NetworkException
import com.bsamy.musix.model.RequestType
import com.bsamy.musix.model.dtos.music.MusicDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.reflect.Type

class MusicRepositoryImpTest {

    @Test
    fun `searchForMusic() with query and limit and userToken then emit listOf MusicDto`() =
        runBlocking {
            //arrange
            val expectedReturn = listOf(MusicDto(id = 1), MusicDto(id = 2))
            val network = object : Network {

                override fun <T> fetch(
                    url: String,
                    requestType: RequestType,
                    headers: Map<String, String>?,
                    queries: Map<String, Any>?,
                    returnType: Type
                ): T = expectedReturn as T

            }
            val musicRepository = MusicRepositoryImp(network)

            //act
            val result = musicRepository.searchForMusic(query = "Hi", userToken = "ldfsj").first()

            //assert
            assert(result == expectedReturn)
        }

    @Test(expected = NetworkException::class)
    fun `searchForMusic() with query and limit and userToken then throws NetworkException`() =
        runBlocking {
            //arrange
            val expectedReturn = listOf(MusicDto(id = 1), MusicDto(id = 2))
            val network = object : Network {

                override fun <T> fetch(
                    url: String,
                    requestType: RequestType,
                    headers: Map<String, String>?,
                    queries: Map<String, Any>?,
                    returnType: Type
                ): T = throw NetworkException(500, "")

            }
            val musicRepository = MusicRepositoryImp(network)

            //act
            val result = musicRepository.searchForMusic(query = "Hi", userToken = "ldfsj").first()

        }
}