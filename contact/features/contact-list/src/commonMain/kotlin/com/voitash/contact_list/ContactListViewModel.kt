package com.voitash.contact_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voitash.contact_domain.model.Contact
import com.voitash.contact_domain.state.Resource
import com.voitash.contact_domain.usecase.CheckEmptyDBUseCase
import com.voitash.contact_domain.usecase.ContactListUseCase
import com.voitash.contact_domain.usecase.DeleteContactUseCase
import com.voitash.contact_domain.usecase.ResetContactsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val checkEmptyDBUseCase: CheckEmptyDBUseCase,
    private val resetContactsUseCase: ResetContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    contactListUseCase: ContactListUseCase
): ViewModel() {

    private val _sideEffect by lazy { Channel<SideEffect>() }
    val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }
    val state: StateFlow<Resource<List<Contact>>> = contactListUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading)
    init {
        viewModelScope.launch {
            val result: Resource<Unit> = checkEmptyDBUseCase()
            if (result is Resource.Error) {
                _sideEffect.send(SideEffect.ShowCannotLoadContacts(result.throwable))
            }
        }
    }
    fun refreshContacts() {
        viewModelScope.launch {
            val result = resetContactsUseCase()
            if (result is Resource.Error) {
                _sideEffect.send(SideEffect.ShowCannotRefreshContacts(result.throwable))
            }
        }
    }
    fun deleteContactById(id: Int) {
        viewModelScope.launch {
            deleteContactUseCase(id)
        }
    }
}
sealed interface SideEffect {
    data class ShowCannotLoadContacts(val t: Throwable): SideEffect
    data class ShowCannotRefreshContacts(val t: Throwable): SideEffect
}