package com.voitash.contact_domain.repository

import com.voitash.contact_domain.model.Contact
import com.voitash.contact_domain.model.ContactWithoutId
import kotlinx.coroutines.flow.Flow

interface ContactsLocalRepository {
    fun allContacts(): Flow<List<Contact>>
    suspend fun contactById(id: Int): Contact?
    suspend fun insertContact(contact: Contact)
    suspend fun insertContacts(contacts: List<ContactWithoutId>)
    suspend fun getContactCount(): Int
    suspend fun deleteContactById(id: Int)
    suspend fun deleteAllFromDB()
}