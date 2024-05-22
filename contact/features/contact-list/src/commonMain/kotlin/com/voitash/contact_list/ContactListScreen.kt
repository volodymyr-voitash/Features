package com.voitash.contact_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.voitash.contact_domain.model.Contact
import com.voitash.contact_domain.state.Resource
import com.voitash.contact_list.resources.Res
import com.voitash.contact_list.resources.cannot_load_contacts
import com.voitash.contact_list.resources.unknown_error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.lifecycle.LifecycleOwner
import moe.tlaster.precompose.lifecycle.LocalLifecycleOwner
import moe.tlaster.precompose.lifecycle.repeatOnLifecycle
import org.jetbrains.compose.resources.getString
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun ContactListScreen(onNavigateToContactDetails: (Int) -> Unit, viewModel: ContactListViewModel) {
    val state: Resource<List<Contact>> by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        floatingActionButton = { RefreshBtn { viewModel.refreshContacts() } },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    )
    { paddings: PaddingValues ->
        when(val currentState = state) {
            is Resource.Success<List<Contact>> -> ContactList(
                currentState.data,
                onNavigateToContactDetails,
                { viewModel.deleteContactById(it) },
                paddings
            )
            is Resource.Loading -> Loading()
            is Resource.Error -> Error(currentState.throwable)
        }
        CollectSideEffect(viewModel.sideEffect) {
            when(it) {
                is SideEffect.ShowCannotLoadContacts -> {
                    snackbarHostState.showSnackbar(
                        getString(Res.string.cannot_load_contacts,
                        it.t.message ?: getString(Res.string.unknown_error)
                        )
                    )
                }
                is SideEffect.ShowCannotRefreshContacts -> {
                    snackbarHostState.showSnackbar(
                        getString(Res.string.cannot_load_contacts, it.t.message
                            ?: getString(Res.string.unknown_error)
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ContactList(
    contacts: List<Contact>,
    onClick: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    paddings: PaddingValues,
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddings)
    ) {
        items(
            items = contacts,
            key = { contact -> contact.id }
        ) {
            ContactCard(it, onClick, onDelete)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.ContactCard(contact: Contact, onClick: (Int) -> Unit, onDelete: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(size = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .animateItemPlacement()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) {
                onClick(contact.id)
            }
    ) {
        Row(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            ContactImage(url = contact.photo)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                text = contact.formatName(),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis
            )
            DeleteBtn(onClick = { onDelete(contact.id) })
        }
    }
}

@Composable
fun ContactImage(url: String?) {
    val placeholder = rememberVectorPainter(image = Icons.Rounded.AccountCircle)
    AsyncImage(
        model = url,
        contentDescription = "Contact image",
        placeholder = placeholder,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(64.dp)
            .aspectRatio(1f / 1f)
            .clip(CircleShape)
    )
}

@Composable
fun RowScope.DeleteBtn(onClick: () -> Unit) {
    IconButton(onClick = onClick, modifier = Modifier.align(Alignment.CenterVertically)) {
        Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete button")
    }
}

@Composable
fun Loading() {
    Box(
        Modifier
            .padding(8.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun Error(throwable: Throwable) {
    Box(
        Modifier
            .padding(8.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = throwable.message ?: "Unknown error")
    }
}

@Composable
fun RefreshBtn(onClick: () -> Unit) {
    FloatingActionButton(onClick = { onClick() },) {
        Icon(Icons.Filled.Refresh, "Floating action button.")
    }
}

@Composable
fun <SideEffect> CollectSideEffect(
    sideEffect: Flow<SideEffect>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: CoroutineContext = Dispatchers.Main.immediate,
    onSideEffect: suspend CoroutineScope.(effect: SideEffect) -> Unit,
) {
    LaunchedEffect(sideEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle {
            if (context == EmptyCoroutineContext) {
                sideEffect.collect { onSideEffect(it) }
            } else {
                withContext(context) {
                    sideEffect.collect { onSideEffect(it) }
                }
            }
        }
    }
}