import com.voitash.contact_domain.dispatchers.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainCoroutineDispatcher

class AppDispatchersProvider: DispatchersProvider {
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val main: MainCoroutineDispatcher
        get() = Dispatchers.Main
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}