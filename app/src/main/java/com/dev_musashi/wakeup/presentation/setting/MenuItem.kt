package com.dev_musashi.wakeup.presentation.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    text: String,
    state: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
        Switch(
            checked = state,
            onCheckedChange = { onClick() },
            colors = SwitchDefaults.colors(Color.White)
        )
    }
}