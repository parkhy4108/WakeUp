package com.dev_musashi.wakeup.presentation.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dev_musashi.wakeup.R

@Composable
fun CustomTopBar(
    modifier : Modifier = Modifier,
    onClick: ()->Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Setting",
            modifier = Modifier,
            fontSize = 37.sp,
            fontWeight = FontWeight.Light,
            color = Color.White
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_save),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.clickable { onClick() }
        )
    }
}