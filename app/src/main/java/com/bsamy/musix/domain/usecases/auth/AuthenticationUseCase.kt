package com.bsamy.musix.domain.usecases.auth

import com.bsamy.musix.model.repos.auth.AuthenticationRepository
import com.bsamy.musix.model.repos.auth.authenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val authenticationUseCase: AuthenticationUseCase by lazy { AuthenticationUseCaseImp() }

interface AuthenticationUseCase {

    suspend fun authenticate(): Flow<String>
}

class AuthenticationUseCaseImp(private val authenticationRepo: AuthenticationRepository = authenticationRepository) :
    AuthenticationUseCase {

    override suspend fun authenticate() =
        authenticationRepo.refreshToken().map { it.accessToken!! }
}