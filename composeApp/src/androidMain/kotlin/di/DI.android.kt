package di

import androidx.lifecycle.ViewModel
import com.voitash.contact_database.contacts.ContactsDao
import com.voitash.contact_database.getDatabaseBuilder
import com.voitash.contact_database.getRoomDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<ContactsDao> { getRoomDatabase(getDatabaseBuilder(get())).contactDao() }
}

actual inline fun <reified T : ViewModel> Module.viewModelDefinition(
    qualifier: Qualifier?,
    noinline definition: Definition<T>,
): KoinDefinition<T> = viewModel(qualifier = qualifier, definition = definition)
