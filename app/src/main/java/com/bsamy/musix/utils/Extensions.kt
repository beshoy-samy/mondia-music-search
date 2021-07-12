package com.bsamy.musix.utils

import java.text.SimpleDateFormat
import java.util.*

fun Int?.orZero() = this ?: 0

fun Int?.orNotFound() = this ?: AppConstants.NOT_FOUND

fun Float?.orNotFound() = this ?: AppConstants.NOT_FOUND.toFloat()

fun String?.parseToDate(
    format: String = AppConstants.DOMAIN_DATE_FORMAT,
    locale: Locale = Locale.getDefault()
): String? = this?.let {
    val date = this.toDate() ?: return@let null
    SimpleDateFormat(format, locale).format(date)
}

fun String?.toDate(withTimeZone: Boolean = true): Date? = this?.let {
    val dateFormatter =
        SimpleDateFormat(AppConstants.INCOMING_DATE_FORMAT, Locale.getDefault())
    if (withTimeZone) dateFormatter.timeZone = TimeZone.getTimeZone("GMT")
    dateFormatter.parse(this)
}