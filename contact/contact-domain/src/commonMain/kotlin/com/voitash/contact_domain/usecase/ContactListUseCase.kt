package com.voitash.contact_domain.usecase

import com.voitash.contact_domain.model.Contact
import com.voitash.contact_domain.repository.ContactsLocalRepository
import com.voitash.contact_domain.state.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ContactListUseCase(private val localRepository: ContactsLocalRepository) {
    operator fun invoke(): Flow<Resource<List<Contact>>> {
        return flow {
            try {
                localRepository.allContacts()
                    .map { Resource.Success(it) }
                    .collect {
                        this.emit(it)
                    }
            } catch (e: Exception) {
                this.emit(Resource.Error(e))
            }
        }
    }
}