package com.bsamy.musix.utils

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.bsamy.musix.R
import com.bsamy.musix.model.ImageLoader
import com.bsamy.musix.model.imageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

fun AppCompatImageView.loadImage(
    url: String,
    loader: ImageLoader = imageLoader,
    @DrawableRes placeholder: Int = R.drawable.ic_image_placeholder,
    @DrawableRes errorPlaceholder: Int = R.drawable.ic_broken_image
) {
    CoroutineScope(Dispatchers.IO).launch {
        loader.loadImage(url)
            .onStart { setImageResource(placeholder) }
            .catch {
                withContext(Dispatchers.Main) { setImageResource(errorPlaceholder) }
                Log.e("ImageLoader", "", it)
            }.collect { bitmap ->
                withContext(Dispatchers.Main) { setImageBitmap(bitmap) }
            }
    }
}