package com.dev_musashi.wakeup.presentation.screen.setting

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SwitchRow(
    modifier: Modifier = Modifier,
    text: String,
    state: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSystemInDarkTheme()) Color.White else Color.Black
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = text, fontSize = 20.sp, color = color)
        Switch(
            modifier = Modifier.height(30.dp),
            checked = state,
            onCheckedChange = { onClick() },
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray,
            )
        )
    }
}