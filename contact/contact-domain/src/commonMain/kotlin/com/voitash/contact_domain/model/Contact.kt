package com.voitash.contact_domain.model

data class Contact(
    val id: Int,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val photo: String?
) {
    fun formatName(): String = buildString {
        firstName?.let {
            append(it)
            append(" ")
        }
        lastName?.let {
            append(it)
        }
    }
}