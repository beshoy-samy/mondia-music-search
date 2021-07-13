package com.bsamy.musix.domain.usecases.auth

import com.bsamy.musix.model.dtos.AuthenticationDto
import com.bsamy.musix.model.repos.auth.AuthenticationRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AuthenticationUseCaseImpTest {

    @Test
    fun `authenticate() then emit user token string`() = runBlocking {
        //arrange
        val expectedReturn = "sldfjsns"
        val repository = object : AuthenticationRepository {
            override suspend fun refreshToken() =
                flowOf(AuthenticationDto(accessToken = expectedReturn))
        }
        val authenticationUseCase = AuthenticationUseCaseImp(repository)

        //act
        val result = authenticationUseCase.authenticate().first()

        assert(expectedReturn == result)
    }

    @Test(expected = Exception::class)
    fun `authenticate() then throw exception`() = runBlocking {
        //arrange
        val repository = object : AuthenticationRepository {
            override suspend fun refreshToken() = throw Exception()
        }
        val authenticationUseCase = AuthenticationUseCaseImp(repository)

        //act
        val result = authenticationUseCase.authenticate().first()
    }

}