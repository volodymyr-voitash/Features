package com.voitash.contact_data.repository

import com.voitash.contact_data.mappers.toContact
import com.voitash.contact_data.mappers.toDbContact
import com.voitash.contact_database.contacts.ContactsDao
import com.voitash.contact_domain.model.Contact
import com.voitash.contact_domain.model.ContactWithoutId
import com.voitash.contact_domain.repository.ContactsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactsLocalRepositoryImpl(
    private val dao: ContactsDao
): ContactsLocalRepository {

    override fun allContacts(): Flow<List<Contact>> =
        dao.getContacts()
            .map { list -> list.map { it.toContact() } }

    override suspend fun contactById(id: Int): Contact? =
        dao.getContactById(id)?.toContact()

    override suspend fun insertContact(contact: Contact) {
        dao.update(contact.toDbContact())
    }

    override suspend fun insertContacts(contacts: List<ContactWithoutId>) {
        dao.addAll(contacts.map { contact -> contact.toDbContact() })
    }

    override suspend fun getContactCount(): Int = dao.contactCount()

    override suspend fun deleteContactById(id: Int) = dao.deleteById(id)

    override suspend fun deleteAllFromDB() = dao.deleteAll()

}