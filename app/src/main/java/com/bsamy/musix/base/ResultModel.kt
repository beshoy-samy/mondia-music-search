package com.bsamy.musix.base

import android.content.Context
import com.bsamy.musix.R
import com.bsamy.musix.model.NetworkException
import java.io.IOException

sealed class ResultModel<out T : Any> {

    data class SuccessResult<out T : Any>(val data: T) : ResultModel<T>()

    data class ErrorResult(val throwable: Throwable?) : ResultModel<Nothing>()

    object Progress : ResultModel<Nothing>()

    object Idle : ResultModel<Nothing>()
}

fun ResultModel.ErrorResult.getErrorMessage(context: Context) =
    throwable?.let {
        when (it) {
            is NetworkException -> context.getString(it.getErrorMessage())
            is IOException -> context.getString(R.string.network_error)
            else -> context.getString(R.string.unknown_error)
        }
    } ?: context.getString(R.string.unknown_error)