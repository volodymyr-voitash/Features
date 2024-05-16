package com.voitash.contact_domain.usecase

import com.voitash.contact_domain.dispatchers.DispatchersProvider
import com.voitash.contact_domain.repository.ContactsLocalRepository
import com.voitash.contact_domain.repository.ContactsRemoteRepository
import com.voitash.contact_domain.state.Resource
import kotlinx.coroutines.withContext

class CheckEmptyDBUseCase(
    private val localRepository: ContactsLocalRepository,
    private val remoteRepository: ContactsRemoteRepository,
    private val dispatchers: DispatchersProvider
) {
    suspend operator fun invoke(): Resource<Unit> = withContext(dispatchers.io) {
        val contactNumber = localRepository.getContactCount()
        if (contactNumber == 0) {
            try {
                val apiContacts = remoteRepository.fetchContacts()
                localRepository.insertContacts(apiContacts)
            }catch (e: Exception) {
                return@withContext Resource.Error(e)
            }
        }
        return@withContext Resource.Success(Unit)
    }
}