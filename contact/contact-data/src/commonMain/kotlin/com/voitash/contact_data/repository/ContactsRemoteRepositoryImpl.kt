package com.voitash.contact_data.repository

import com.voitash.contact_api.ContactApi
import com.voitash.contact_data.mappers.toContact
import com.voitash.contact_domain.model.ContactWithoutId
import com.voitash.contact_domain.repository.ContactsRemoteRepository

class ContactsRemoteRepositoryImpl(
    private val api: ContactApi
): ContactsRemoteRepository {
    override suspend fun fetchContacts(number: Int): List<ContactWithoutId> {
        return api.getUsers(number).getOrThrow().results?.map { it.toContact() } ?: emptyList()
    }
}