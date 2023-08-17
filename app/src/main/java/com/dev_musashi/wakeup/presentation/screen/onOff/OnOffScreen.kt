package com.dev_musashi.wakeup.presentation.screen.onOff

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.wakeup.R

@Composable
fun OnOffScreen(
    open: (String) -> Unit,
    viewModel: OnOffViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val infiniteTransition = rememberInfiniteTransition()
    val scale =
        if (state.isTimerClicked) {
            infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000),
                    repeatMode = RepeatMode.Reverse
                )
            )
        } else {
            animateFloatAsState(targetValue = 1f, label = "")
        }

    LaunchedEffect(Unit) {
        viewModel.initSetting()
        if (state.isTimerClicked) {
            viewModel.startTimer(open)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Card(
            modifier = Modifier
                .size(220.dp)
                .padding(20.dp)
                .scale(scale.value),
            shape = CircleShape,
            elevation = 17.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            ) {
                Button(
                    modifier = Modifier.fillMaxSize(),
                    onClick = { viewModel.onTimerClicked(open) },
                    shape = CircleShape,
                    border = BorderStroke(2.dp, Color.White),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1B379C))
                ) {
                    Text(
                        text = state.time,
                        color = Color.White,
                        fontSize = 40.sp,
                        letterSpacing = 4.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Switch(
            checked = state.isTimerClicked,
            onCheckedChange = { viewModel.onTimerClicked(open) },
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray
            )
        )
    }
    if (state.isDialogShowed) {
        AlertDialog(
            onDismissRequest = { },
            text = { Text(text = "알림") },
            confirmButton = {
                TextButton(onClick = { viewModel.onDialogOkayClicked(open) })
                { Text(text = stringResource(id = R.string.confirm)) }
            },
        )
    }
}