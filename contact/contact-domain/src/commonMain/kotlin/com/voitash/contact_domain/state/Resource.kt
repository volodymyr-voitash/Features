package com.voitash.contact_domain.state

sealed class Resource<out T: Any?> {
    data class Success<T>(val data: T): Resource<T>()
    data object Loading: Resource<Nothing>()
    data class Error(val throwable: Throwable): Resource<Nothing>()
}