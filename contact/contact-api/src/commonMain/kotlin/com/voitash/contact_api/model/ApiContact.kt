package com.voitash.contact_api.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiContact(
    val name: Name?,
    val email: String?,
    val picture: Picture?
)