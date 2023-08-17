package com.dev_musashi.wakeup.presentation.util

import androidx.annotation.StringRes
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.dev_musashi.wakeup.R

@Composable
fun ShowDialog(
    @StringRes text: Int,
    onDismissRequest: () ->Unit,
    confirmButton: () -> Unit,
    dismissButton: ()->Unit
){
    AlertDialog(
        onDismissRequest = { onDismissRequest()  },
        text = {
            Text(text = stringResource(id = text))
        },
        confirmButton = {
            TextButton(onClick = { confirmButton() }) {
                Text(text = stringResource(id = R.string.confirm), color = Color.Blue, fontSize = 15.sp)
            }
        },
        dismissButton = {
            TextButton(onClick = { dismissButton() }) {
                Text(text = stringResource(id = R.string.dismiss), color = Color.Blue, fontSize = 15.sp)
            }
        }
    )
}