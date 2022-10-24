package com.dev_musashi.wakeup.presentation.problem

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProblemScreen(
//    problemCnt: Int,
    popUp: () -> Unit,
    viewModel: ProblemViewModel = hiltViewModel()
) {
    val state by viewModel.state

    LaunchedEffect(Unit) {
//        viewModel.makeProblem(problemCnt, popUp)

        viewModel.start(popUp)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "잠 깨!!!", fontSize = 30.sp, color = Color.White)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "${state.timer} 초", fontSize = 30.sp, color = Color.White)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = state.check, fontSize = 30.sp, color = Color.White)
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "${state.leftNum}", fontSize = 30.sp, color = Color.White)
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "X", fontSize = 30.sp, color = Color.White)
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "${state.rightNum}", fontSize = 30.sp, color = Color.White)
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "=", fontSize = 30.sp, color = Color.White)
            Spacer(modifier = Modifier.width(20.dp))
            TextField(
                modifier = Modifier.width(80.dp),
                value = state.answer,
                onValueChange = { viewModel.answerChanged(newValue = it) },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                textStyle = TextStyle.Default.copy(fontSize = 28.sp)
            )
        }

    }
}