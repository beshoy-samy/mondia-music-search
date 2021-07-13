package com.bsamy.musix.model.repos.auth

import com.bsamy.musix.model.Network
import com.bsamy.musix.model.NetworkException
import com.bsamy.musix.model.RequestType
import com.bsamy.musix.model.dtos.AuthenticationDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.reflect.Type

class AuthenticationRepositoryImpTest {

    @Test
    fun `refreshToken() then emit AuthenticationDto`() = runBlocking {
        //arrange
        val expectedReturn = AuthenticationDto(accessToken = "user token 010")
        val network = object : Network {

            override fun <T> fetch(
                url: String,
                requestType: RequestType,
                headers: Map<String, String>?,
                queries: Map<String, Any>?,
                returnType: Type
            ): T = expectedReturn as T

        }
        val authenticationRepository = AuthenticationRepositoryImp(network)

        //act
        val result = authenticationRepository.refreshToken().first()

        //assert
        assert(result == expectedReturn)
    }

    @Test(expected = NetworkException::class)
    fun `refreshToken() then throws NetworkException`() = runBlocking {
        //arrange
        val expectedReturn = AuthenticationDto(accessToken = "user token 010")
        val network = object : Network {

            override fun <T> fetch(
                url: String,
                requestType: RequestType,
                headers: Map<String, String>?,
                queries: Map<String, Any>?,
                returnType: Type
            ): T = throw NetworkException(500, "")

        }
        val authenticationRepository = AuthenticationRepositoryImp(network)

        //act
        val result = authenticationRepository.refreshToken().first()
    }

}