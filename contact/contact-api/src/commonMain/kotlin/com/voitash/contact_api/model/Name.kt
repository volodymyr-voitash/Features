package com.voitash.contact_api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Name(
    @SerialName("first")
    val firstName: String?,
    @SerialName("last")
    val lastName: String?,
    val picture: Picture?
)