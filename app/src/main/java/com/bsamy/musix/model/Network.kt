package com.bsamy.musix.model

import android.util.Log
import com.bsamy.musix.BuildConfig
import com.bsamy.musix.R
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL

val network: Network by lazy { NetworkImp() }

interface Network {

    fun <T> fetch(
        url: String,
        requestType: RequestType,
        headers: Map<String, String>? = null,
        queries: Map<String, Any>? = null,
        returnType: Type
    ): T
}

private const val TAG = "Network"

internal class NetworkImp(private val gson: Gson = gsonSerializer) : Network {

    override fun <T> fetch(
        url: String,
        requestType: RequestType,
        headers: Map<String, String>?,
        queries: Map<String, Any>?,
        returnType: Type
    ): T {
        val builtQueries = buildRequestQueries(queries)
        val requestUrl = "${BuildConfig.BASE_URL}$url$builtQueries"

        val connection = URL(requestUrl).openConnection() as HttpURLConnection
        connection.requestMethod = requestType.key

        headers?.keys?.forEach { key -> connection.setRequestProperty(key, headers[key]) }
        connection.setRequestProperty("Accept", "application/json")

        Log.i(TAG, "headers: $headers")
        Log.i(TAG, "queries: $queries")

        connection.connectTimeout = Int.MAX_VALUE
        connection.readTimeout = Int.MAX_VALUE
        connection.connect()
        val responseMessage = connection.responseMessage
        Log.i(TAG, "request $requestUrl messages that $responseMessage")
        when (val responseCode = connection.responseCode) {
            HttpURLConnection.HTTP_OK -> {
                val response = connection.readResponse()
                connection.disconnect()
                return gson.fromJson(response, returnType) as T
            }
            else -> {
                connection.disconnect()
                throw NetworkException(responseCode, responseMessage)
            }
        }
    }

    private fun buildRequestQueries(queries: Map<String, Any>?) =
        queries?.keys?.let { keys ->
            val lastIndex = keys.size.minus(1)
            var result = "?"
            keys.forEachIndexed { index, key ->
                result += "$key=${queries[key]}"
                if (index != lastIndex) result += "&"
            }
            result
        } ?: ""

}

private val gsonSerializer by lazy<Gson> {
    GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
}

enum class RequestType(val key: String) {
    GET("GET"),
    POST("POST")
}

fun HttpURLConnection.readResponse(): String {
    var response = ""
    try {
        response = inputStream.bufferedReader().readText()
        Log.i(TAG, "parsing: $response")
    } catch (throwable: Throwable) {
        Log.e(TAG, "parsing", throwable)
    }
    return response
}

data class NetworkException(val code: Int, val response: String) : Exception() {

    fun getErrorMessage() =
        when (code) {
            HttpURLConnection.HTTP_INTERNAL_ERROR -> R.string.internal_error_msg
            HttpURLConnection.HTTP_UNAUTHORIZED -> R.string.authenticate_first
            HttpURLConnection.HTTP_NOT_FOUND, HttpURLConnection.HTTP_NO_CONTENT ->
                R.string.not_found_error
            else -> R.string.unknown_error
        }
}