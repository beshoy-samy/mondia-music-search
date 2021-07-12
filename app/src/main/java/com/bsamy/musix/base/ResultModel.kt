package com.bsamy.musix.base

sealed class ResultModel<out T : Any> {

    data class SuccessResult<out T : Any>(val data: T) : ResultModel<T>()

    data class ErrorResult(val throwable: Throwable?) : ResultModel<Nothing>()

    object Progress : ResultModel<Nothing>()

    object Idle : ResultModel<Nothing>()
}