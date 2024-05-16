package com.voitash.contact_api.model

import kotlinx.serialization.Serializable

@Serializable
data class Picture(
    val large: String?,
    val medium: String?,
    val thumbnail: String?
)