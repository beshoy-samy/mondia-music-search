package com.bsamy.musix.utils

fun Int?.orZero() = this ?: 0

fun Int?.orNotFound() = this ?: AppConstants.NOT_FOUND

fun Float?.orNotFound() = this ?: AppConstants.NOT_FOUND.toFloat()