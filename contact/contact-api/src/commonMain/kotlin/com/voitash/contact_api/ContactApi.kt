package com.voitash.contact_api

import com.voitash.contact_api.response.ApiContactResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class ContactApi(private val client: NetworkClient) {

    private val BASE_URL = "https://randomuser.me/"
    private val API = "/api"
    private val LIMIT = "?results="

    suspend fun getUsers(limit: Int): Result<ApiContactResponse>{
        return withContext(Dispatchers.IO) {
            client.getData<ApiContactResponse>("$BASE_URL$API$LIMIT$limit")
        }
    }
}