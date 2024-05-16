package com.voitash.contact_domain.usecase

import com.voitash.contact_domain.dispatchers.DispatchersProvider
import com.voitash.contact_domain.repository.ContactsLocalRepository
import kotlinx.coroutines.withContext

class DeleteContactUseCase(
    private val localRepository: ContactsLocalRepository,
    private val dispatchersProvider: DispatchersProvider
) {
    suspend operator fun invoke(id: Int) = withContext(dispatchersProvider.io) {
        localRepository.deleteContactById(id)
    }
}