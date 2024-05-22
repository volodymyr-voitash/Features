package com.voitash.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voitash.menu.resources.Res
import com.voitash.menu.resources.menu_item_random_contacts
import org.jetbrains.compose.resources.stringResource

@Composable
fun MenuScreen(onContactSelected: () -> Unit) {
    Box(modifier = Modifier.padding(30.dp)) {
        ContactFeature(onContactSelected)
    }
}

@Composable
fun ContactFeature(onContactSelected: () -> Unit) {
    MenuItem(stringResource(Res.string.menu_item_random_contacts), Icons.Filled.AccountBox, onContactSelected)
}

@Composable
fun MenuItem(
    text: String,
    icon: ImageVector,
    onItemSelected: () -> Unit
) {
    Card(
        onClick = onItemSelected,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(60.dp)
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
            )
            Text(
                text = text,
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(20.dp),
            )
        }
    }
}