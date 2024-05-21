package com.voitash.contact_api

import io.ktor.client.*
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

actual fun createHttpClient() : HttpClient {
    return desktopHttpClient()
}

@OptIn(ExperimentalSerializationApi::class)
internal  fun desktopHttpClient() = HttpClient(Java.create()) {
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