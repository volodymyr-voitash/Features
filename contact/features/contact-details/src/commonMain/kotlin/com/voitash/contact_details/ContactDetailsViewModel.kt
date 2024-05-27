package com.voitash.contact_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voitash.contact_domain.model.Contact
import com.voitash.contact_domain.usecase.DeleteContactUseCase
import com.voitash.contact_domain.usecase.GetContactByIdUseCase
import com.voitash.contact_domain.usecase.UpdateContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactDetailsViewModel(
    private val contactId: Int,
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase
): ViewModel() {

    private val _state = MutableStateFlow(EditContactState(id = contactId))
    val state = _state as StateFlow<EditContactState>

    init {
        viewModelScope.launch {
            val contact = getContactByIdUseCase(contactId)
            _state.emit(
                EditContactState(
                    id = contactId,
                    firstName = contact?.firstName,
                    lastName = contact?.lastName,
                    photo = contact?.photo,
                    email = contact?.email
                )
            )
        }
    }

    fun toggleEditingMode() {
        viewModelScope.launch {
            _state.emit(_state.value.toggleEditMode())
        }
    }

    fun deleteContact(onNavigate: () -> Unit) {
        viewModelScope.launch {
            deleteContactUseCase(contactId)
            onNavigate()
        }
    }

    fun updateName(newName: String) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(firstName = newName))
        }
    }

    fun updateEmail(newEmail: String) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(email = newEmail, isSaveEnabled = newEmail.isValidEmail()))
        }
    }

    fun updateLastname(newLastName: String) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(lastName = newLastName))
        }
    }

    fun updateContact(onNavigate: () -> Unit) {
        viewModelScope.launch {
            toggleEditingMode()
            val contact = Contact(
                id = contactId,
                firstName = state.value.firstName,
                lastName = state.value.lastName,
                email = state.value.email,
                photo = state.value.photo
            )
            updateContactUseCase(contact)
            onNavigate()
        }
    }
}

data class EditContactState(
    val isEditingEnabled: Boolean = false,
    val isSaveEnabled: Boolean = true,
    val id: Int,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val photo: String? = null
) {
    fun toggleEditMode() = this.copy(isEditingEnabled = !this.isEditingEnabled)
}