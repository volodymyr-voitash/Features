package com.voitash.contact_api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

expect fun createHttpClient(): HttpClient

class NetworkClient {
    val httpClient = createHttpClient()

    suspend inline fun<reified T> getData(path: String): Result<T> {
        try {
            val result = httpClient.get(path).body<T>()
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}