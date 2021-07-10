package com.bsamy.musix.model

import android.util.Log
import com.bsamy.musix.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.net.HttpURLConnection
import java.net.URL

val network: Network by lazy { NetworkImp() }

interface Network {

    fun <T> fetch(
        url: String,
        requestType: RequestType,
        headers: Map<String, String>? = null,
        queries: Map<String, String>? = null,
        returnType: Class<T>
    ): T
}

internal class NetworkImp(private val gson: Gson = gsonSerializer) : Network {

    private val TAG = "NetworkImp"

    override fun <T> fetch(
        url: String,
        requestType: RequestType,
        headers: Map<String, String>?,
        queries: Map<String, String>?,
        returnType: Class<T>
    ): T {
        val builtQueries = buildRequestQueries(queries)
        val requestUrl = "${BuildConfig.BASE_URL}$url$builtQueries"

        val connection = URL(requestUrl).openConnection() as HttpURLConnection
        connection.requestMethod = requestType.key
        connection.setChunkedStreamingMode(0)
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("Accept-Encoding", "identity")
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        connection.setRequestProperty("Connection", "close")
        headers?.keys?.forEach { key -> connection.setRequestProperty(key, headers[key]) }

        connection.connectTimeout = Int.MAX_VALUE
        connection.readTimeout = Int.MAX_VALUE
        connection.connect()
        val responseCode = connection.responseCode
        val response = connection.stringResponse()
        when (responseCode) {
            HttpURLConnection.HTTP_OK -> {
                connection.disconnect()
                return gson.fromJson(response, returnType)
            }
            else -> {
                connection.disconnect()
                throw NetworkException(responseCode, response)
            }
        }
    }

    private fun buildRequestQueries(queries: Map<String, String>?) =
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

fun HttpURLConnection.stringResponse(): String {
    val response = StringBuffer()
    try {
        inputStream.bufferedReader().use {
            val lines = it.readLines()
            lines.forEach { line -> response.append(line) }
            Log.d("Network Parsing lines", "$response")
        }
    } catch (throwable: Throwable) {
        Log.e("Network Parsing", throwable.message.toString())
        Log.e("Network Parsing", "", throwable)
    } finally {
        inputStream.close()
    }
    return response.toString()
}

data class NetworkException(private val code: Int, val response: String) : Exception()