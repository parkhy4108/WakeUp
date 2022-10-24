package com.dev_musashi.wakeup.util

import androidx.annotation.StringRes
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
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
            Button(onClick = { confirmButton() }) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            Button(onClick = { dismissButton() }) {
                Text(text = stringResource(id = R.string.dismiss))
            }
        },
    )
}