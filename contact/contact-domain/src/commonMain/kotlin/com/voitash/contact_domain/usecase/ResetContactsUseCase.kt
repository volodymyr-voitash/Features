package com.voitash.contact_domain.usecase

import com.voitash.contact_domain.dispatchers.DispatchersProvider
import com.voitash.contact_domain.repository.ContactsLocalRepository
import com.voitash.contact_domain.repository.ContactsRemoteRepository
import com.voitash.contact_domain.state.Resource
import kotlinx.coroutines.withContext

class ResetContactsUseCase(
    private val localRepository: ContactsLocalRepository,
    private val remoteRepository: ContactsRemoteRepository,
    private val dispatchers: DispatchersProvider
) {
    suspend operator fun invoke(): Resource<Unit> = withContext(dispatchers.io) {
        try {
            val apiContacts = remoteRepository.fetchContacts()
            if (apiContacts.isNotEmpty()) {
                localRepository.deleteAllFromDB()
                localRepository.insertContacts(apiContacts)
            }
        }catch (e: Exception) {
            return@withContext Resource.Error(e)
        }
        return@withContext Resource.Success(Unit)
    }
}