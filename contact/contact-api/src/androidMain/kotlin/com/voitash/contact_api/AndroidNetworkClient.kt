package com.voitash.contact_api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

actual fun createHttpClient() : HttpClient {
    return androidHttpClient()
}

@OptIn(ExperimentalSerializationApi::class)
internal  fun androidHttpClient() = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            explicitNulls = false
        })
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }
}