package di

import AppDispatchersProvider
import androidx.lifecycle.ViewModel
import com.voitash.contact_api.ContactApi
import com.voitash.contact_api.NetworkClient
import com.voitash.contact_data.repository.ContactsLocalRepositoryImpl
import com.voitash.contact_data.repository.ContactsRemoteRepositoryImpl
import com.voitash.contact_details.ContactDetailsViewModel
import com.voitash.contact_domain.dispatchers.DispatchersProvider
import com.voitash.contact_domain.repository.ContactsLocalRepository
import com.voitash.contact_domain.repository.ContactsRemoteRepository
import com.voitash.contact_domain.usecase.CheckEmptyDBUseCase
import com.voitash.contact_domain.usecase.ContactListUseCase
import com.voitash.contact_domain.usecase.DeleteContactUseCase
import com.voitash.contact_domain.usecase.GetContactByIdUseCase
import com.voitash.contact_domain.usecase.ResetContactsUseCase
import com.voitash.contact_domain.usecase.UpdateContactUseCase
import com.voitash.contact_list.ContactListViewModel
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

expect inline fun <reified T : ViewModel> Module.viewModelDefinition(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>

fun dataModule(): Module {
    return module {
        single<DispatchersProvider> { AppDispatchersProvider() }
        single { ContactApi(NetworkClient()) }
        single<ContactsLocalRepository> { ContactsLocalRepositoryImpl(get()) }
        single<ContactsRemoteRepository> { ContactsRemoteRepositoryImpl(get()) }
    }
}

fun usecaseModule(): Module {
    return module {
        factory { CheckEmptyDBUseCase(get(), get(), get()) }
        factory { ContactListUseCase(get()) }
        factory { DeleteContactUseCase(get(), get()) }
        factory { GetContactByIdUseCase(get(), get()) }
        factory { ResetContactsUseCase(get(), get(), get()) }
        factory { UpdateContactUseCase(get(), get()) }
    }
}

fun vmModule(): Module {
    return module {
        viewModelDefinition  { ContactListViewModel(get(), get(), get(), get()) }
        viewModelDefinition { ContactDetailsViewModel(get(), get(), get(), get()) }
    }
}

object KoinDI {
    var di: Koin? = null

    private fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
        appDeclaration()
        modules(platformModule(), dataModule(), usecaseModule(), vmModule())
    }

    fun setupKoin(appDeclaration: KoinAppDeclaration = {}) {
        if (di == null) {
            di = initKoin(appDeclaration).koin
        }
    }
}