package com.voitash.contact_domain.usecase

import com.voitash.contact_domain.dispatchers.DispatchersProvider
import com.voitash.contact_domain.model.Contact
import com.voitash.contact_domain.repository.ContactsLocalRepository
import kotlinx.coroutines.withContext

class UpdateContactUseCase(
    private val contactsLocalRepository: ContactsLocalRepository,
    private val dispatchers: DispatchersProvider
) {
    suspend operator fun invoke(contact: Contact) = withContext(dispatchers.io) {
        contactsLocalRepository.insertContact(contact)
    }
}