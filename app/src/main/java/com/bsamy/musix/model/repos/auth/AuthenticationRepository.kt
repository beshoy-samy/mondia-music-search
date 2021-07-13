package com.bsamy.musix.model.repos.auth

import com.bsamy.musix.BuildConfig
import com.bsamy.musix.model.Network
import com.bsamy.musix.model.RequestType
import com.bsamy.musix.model.dtos.AuthenticationDto
import com.bsamy.musix.model.network
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val authenticationRepository: AuthenticationRepository by lazy { AuthenticationRepositoryImp() }

interface AuthenticationRepository {

    suspend fun refreshToken(): Flow<AuthenticationDto>
}

class AuthenticationRepositoryImp(private val networkConnection: Network = network) :
    AuthenticationRepository {

    override suspend fun refreshToken(): Flow<AuthenticationDto> =
        flow {
            val result =
                networkConnection.fetch<AuthenticationDto>(
                    url = "v0/api/gateway/token/client",
                    RequestType.POST,
                    mapOf("X-MM-GATEWAY-KEY" to BuildConfig.GATWAY_KEY),
                    returnType = AuthenticationDto::class.java
                )
            emit(result)
        }

}