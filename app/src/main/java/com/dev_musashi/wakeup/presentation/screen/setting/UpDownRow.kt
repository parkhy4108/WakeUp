package com.dev_musashi.wakeup.presentation.screen.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev_musashi.wakeup.R

@Composable
fun UpDownRow(
    modifier : Modifier = Modifier,
    title: String,
    text: String,
    onDownClicked: ()->Unit,
    onUpClicked: ()->Unit
){
    val color = if (isSystemInDarkTheme()) Color.White else Color.Black
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, fontSize = 20.sp, color = color)
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                tint = color,
                modifier = Modifier.clickable { onDownClicked() },
            )
            Text(text = text, color = color, fontSize = 20.sp)
            Icon(
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = null,
                tint = color,
                modifier = Modifier.clickable { onUpClicked() }
            )
        }
    }
}
