package com.bsamy.musix.base

sealed class Result<out T : Any> {

    data class SuccessResult<out T : Any>(val data: T) : Result<T>()

    data class ErrorResult(val throwable: Throwable?) : Result<Nothing>()

    object Progress : Result<Nothing>()

    object Idle : Result<Nothing>()
}