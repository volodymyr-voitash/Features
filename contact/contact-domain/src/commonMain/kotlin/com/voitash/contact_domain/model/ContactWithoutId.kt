package com.voitash.contact_domain.model

data class ContactWithoutId(
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val photo: String?
)