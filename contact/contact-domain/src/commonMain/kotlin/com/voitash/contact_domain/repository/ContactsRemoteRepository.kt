package com.voitash.contact_domain.repository

import com.voitash.contact_domain.model.ContactWithoutId

const val DEFAULT_USER_SIZE = 50

interface ContactsRemoteRepository {
    suspend fun fetchContacts(number: Int = DEFAULT_USER_SIZE): List<ContactWithoutId>
}