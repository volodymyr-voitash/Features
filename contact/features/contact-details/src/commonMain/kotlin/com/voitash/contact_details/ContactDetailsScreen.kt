package com.voitash.contact_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ContactDetailsScreen(viewModel: ContactDetailsViewModel, onNavigateBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    ContactDetails(
        state,
        onDelete =  { viewModel.deleteContact(onNavigateBack) },
        onNameChanged = { viewModel.updateName(it)},
        onEmailChanged = { viewModel.updateEmail(it)},
        onLastNameChanged = { viewModel.updateLastname(it) },
        onSave = { viewModel.updateContact(onNavigateBack) },
        onToggleEditingMode = { viewModel.toggleEditingMode() },
        onNavigateBack = onNavigateBack
    )
}
@Composable
fun ContactDetails(
    state: EditContactState,
    onEmailChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onDelete: (Int) -> Unit,
    onSave: () -> Unit,
    onToggleEditingMode: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val openDialog = rememberSaveable { mutableStateOf(false)  }
        Box(modifier = Modifier.fillMaxWidth()) {
            BackBtn(onNavigateBack)
            UserImage(url = state.photo)
        }
        Spacer(modifier = Modifier.size(16.dp))
        TextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            enabled = state.isEditingEnabled,
            value = state.firstName ?: "",
            onValueChange = { newValue ->
                onNameChanged(newValue)
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        TextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            enabled = state.isEditingEnabled,
            value = state.lastName ?: "",
            onValueChange = { newValue ->
                onLastNameChanged(newValue)
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        TextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            enabled = state.isEditingEnabled,
            value = state.email ?: "",
            onValueChange = { newValue ->
                onEmailChanged(newValue)
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.weight(1f),
                enabled = state.isSaveEnabled,
                onClick = { onSave() },
            ) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = { openDialog.value = true },
            ) {
                Text(text = "Delete")
            }
            Spacer(modifier = Modifier.size(16.dp))
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = { onToggleEditingMode() }
            ) {
                if (state.isEditingEnabled) {
                    Icon(imageVector = Icons.Rounded.Close, contentDescription = "Cancel Editing")
                } else {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = "Edit")
                }
            }
        }

        if (openDialog.value) {
            ConfirmDialog(
                title = "Delete contact",
                text = "Are you sure?",
                onConfirm = {
                    openDialog.value = false
                    onDelete(state.id)
                },
                onDismiss = { openDialog.value = false }
            )
        }
    }
}

@Composable
fun BoxScope.UserImage(url: String?) {
    val placeholder = rememberVectorPainter(image = Icons.Rounded.AccountCircle)
    AsyncImage(
        model = url,
        contentDescription = "Contact image",
        placeholder = placeholder,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .align(Alignment.Center)
            .size(128.dp)
            .aspectRatio(1f / 1f)
            .clip(CircleShape)
    )
}

@Composable
fun BoxScope.BackBtn(onBackBtnClicked: () -> Unit) {
    IconButton(onClick = onBackBtnClicked, modifier = Modifier.align(Alignment.TopStart)) {
        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
    }
}

@Composable
fun ConfirmDialog(
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(title) },
        text = { Text(text) },
        confirmButton = {
            Button(onClick = { onConfirm() }) { Text("Confirm") }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) { Text("Dismiss") }
        }
    )
}