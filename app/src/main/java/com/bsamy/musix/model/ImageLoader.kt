package com.bsamy.musix.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.HttpURLConnection
import java.net.URL

val imageLoader: ImageLoader by lazy { ImageLoaderImp() }

interface ImageLoader {

    suspend fun loadImage(url: String): Flow<Bitmap>
}

private class ImageLoaderImp : ImageLoader {

    override suspend fun loadImage(url: String) = flow<Bitmap> {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connectTimeout = Int.MAX_VALUE
        connection.readTimeout = Int.MAX_VALUE
        connection.connect()
        val inputStream = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(inputStream)
        emit(bitmap)
        connection.disconnect()
    }

}