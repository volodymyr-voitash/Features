package com.voitash.contact_domain.usecase

import com.voitash.contact_domain.dispatchers.DispatchersProvider
import com.voitash.contact_domain.model.Contact
import com.voitash.contact_domain.repository.ContactsLocalRepository
import kotlinx.coroutines.withContext

class GetContactByIdUseCase(
    private val localContactsLocalRepository: ContactsLocalRepository,
    private val dispatchers: DispatchersProvider
) {
    suspend operator fun invoke(contactId: Int): Contact? = withContext(dispatchers.io) {
        localContactsLocalRepository.contactById(contactId)
    }
}