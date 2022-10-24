package com.dev_musashi.wakeup.presentation.onOff

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.wakeup.R
import com.dev_musashi.wakeup.util.ShowDialog

@Composable
fun OnOffScreen(
    open: (String)->Unit,
    viewModel: OnOffViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val infiniteTransition = rememberInfiniteTransition()
    val scale = if (state.buttonState) {
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            )
        )
    } else {
        animateFloatAsState(targetValue = 1f)
    }

    LaunchedEffect(Unit){
        viewModel.initSetting()
        if(state.buttonState && !viewModel.timerRunning) {
            viewModel.startCountdownTimer(open)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(state.time, color = Color.White, fontSize = 40.sp)
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .size(180.dp)
                .scale(scale.value)
        ) {
            Button(
                modifier = Modifier
                    .size(180.dp),
                onClick = { viewModel.buttonClick(open) },
                shape = CircleShape,
                border = BorderStroke(2.dp, Color.White),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1976D2))
            ) {
                Text(
                    text = state.buttonText,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Switch(
            checked = state.buttonState,
            onCheckedChange = { viewModel.buttonClick(open) },
            colors = SwitchDefaults.colors(Color.White)
        )
    }
    if(state.showDialog) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = { viewModel.onConfirmClicked(open)})
                { Text(text = stringResource(id = R.string.confirm)) }
            },
        )
    }
}