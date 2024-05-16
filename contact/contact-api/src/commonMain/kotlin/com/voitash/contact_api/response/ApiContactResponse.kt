package com.voitash.contact_api.response

import com.voitash.contact_api.model.ApiContact
import kotlinx.serialization.Serializable

@Serializable
data class ApiContactResponse(val results: List<ApiContact>?)