package com.dev_musashi.wakeup.presentation.screen.problem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.wakeup.presentation.util.addFocusCleaner
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProblemScreen(
    popUp: () -> Unit,
    viewModel: ProblemViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        if (!state.init) {
            focusRequester.requestFocus()
            delay(100)
            keyboard?.show()
            viewModel.start(popUp)

        }
    }

    Column(
        modifier = Modifier.fillMaxSize().addFocusCleaner(focusManager),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(300.dp),
            backgroundColor = Color(0xFFFFECB3),
            elevation = 20.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "${state.timer} 초 남았습니다.", fontSize = 30.sp, color = Color.Black)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "${state.leftNum?: ""}", fontSize = 30.sp, color = Color.Black)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "X", fontSize = 30.sp, color = Color.Black)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "${state.rightNum?: ""}", fontSize = 30.sp, color = Color.Black)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "=", fontSize = 30.sp, color = Color.Black)
                    Spacer(modifier = Modifier.width(20.dp))
                    TextField(
                        modifier = Modifier
                            .width(80.dp)
                            .focusRequester(focusRequester),
                        value = state.answer,
                        onValueChange = { viewModel.answerChanged(newValue = it) },
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFFFECB3) ),
                        textStyle = TextStyle.Default.copy(
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
                Text(text = state.check, fontSize = 30.sp, color = Color.Black)
            }
        }
    }
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(text = "${state.timer} 초", fontSize = 30.sp, color = Color.White)
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(text = state.check, fontSize = 30.sp, color = Color.White)
//        Spacer(modifier = Modifier.height(20.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text(text = "${state.leftNum}", fontSize = 30.sp, color = fontColor)
//            Spacer(modifier = Modifier.width(20.dp))
//            Text(text = "X", fontSize = 30.sp, color = fontColor)
//            Spacer(modifier = Modifier.width(20.dp))
//            Text(text = "${state.rightNum}", fontSize = 30.sp, color = fontColor)
//            Spacer(modifier = Modifier.width(20.dp))
//            Text(text = "=", fontSize = 30.sp, color = fontColor)
//            Spacer(modifier = Modifier.width(20.dp))
//            TextField(
//                modifier = Modifier
//                    .width(80.dp)
//                    .focusRequester(focusRequester),
//                value = state.answer,
//                onValueChange = { viewModel.answerChanged(newValue = it) },
//                colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
//                textStyle = TextStyle.Default.copy(fontSize = 28.sp)
//            )
//        }
//    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun Preview() {
    val fontColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val focusRequester = remember {
        FocusRequester()
    }
    val keyboard = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        delay(100)
        keyboard?.show()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(300.dp),
            backgroundColor = Color(0xFFFFECB3),
            elevation = 20.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "5 초", fontSize = 30.sp, color = fontColor)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "7", fontSize = 30.sp, color = Color.Black)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "X", fontSize = 30.sp, color = Color.Black)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "3", fontSize = 30.sp, color = Color.Black)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "=", fontSize = 30.sp, color = Color.Black)
                    Spacer(modifier = Modifier.width(20.dp))
                    TextField(
                        modifier = Modifier
                            .width(80.dp)
                            .focusRequester(focusRequester),
                        value = "",
                        onValueChange = { },
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFFFECB3) ),
                        textStyle = TextStyle.Default.copy(
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
                Text(text = "정답", fontSize = 30.sp, color = Color.Black)
            }
        }
    }

//    Box(modifier = Modifier.fillMaxSize()) {
//        Card(
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .width(300.dp)
//                .height(300.dp)
//        ) {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.SpaceAround
//            ) {
//                Text(text = "5 초", fontSize = 30.sp, color = fontColor)
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text(text = "7", fontSize = 30.sp, color = fontColor)
//                    Spacer(modifier = Modifier.width(20.dp))
//                    Text(text = "X", fontSize = 30.sp, color = fontColor)
//                    Spacer(modifier = Modifier.width(20.dp))
//                    Text(text = "3", fontSize = 30.sp, color = fontColor)
//                    Spacer(modifier = Modifier.width(20.dp))
//                    Text(text = "=", fontSize = 30.sp, color = fontColor)
//                    Spacer(modifier = Modifier.width(20.dp))
//                    TextField(
//                        modifier = Modifier.width(80.dp),
//                        value = "21",
//                        onValueChange = { },
//                        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
//                        textStyle = TextStyle.Default.copy(
//                            fontSize = 28.sp,
//                            textAlign = TextAlign.Center
//                        )
//                    )
//                }
//                Text(text = "정답", fontSize = 30.sp, color = fontColor)
//            }
//        }
//    }
}

