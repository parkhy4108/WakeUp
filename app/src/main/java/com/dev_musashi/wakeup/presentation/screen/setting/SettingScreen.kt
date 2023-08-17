package com.dev_musashi.wakeup.presentation.screen.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.wakeup.R
import com.dev_musashi.wakeup.presentation.util.ShowDialog
import com.dev_musashi.wakeup.presentation.util.addFocusCleaner

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val color = if (isSystemInDarkTheme()) Color.White else Color.Black
    val focusManager = LocalFocusManager.current
    LaunchedEffect(Unit) {
        viewModel.initSetting()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .addFocusCleaner(focusManager),
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        CustomTopBar(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.showDialog() },
            btnBoolean = state.time.isNotEmpty() || state.time.isNotBlank()
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(35.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "시간 간격", fontSize = 20.sp, color = color)
                TextField(
                    modifier = Modifier
                        .width(80.dp)
                        .height(50.dp),
                    value = state.time,
                    onValueChange = { viewModel.onTimeChanged(it) },
                    placeholder = { Text("최대 60분", color = Color.DarkGray, fontSize = 10.sp) },
                    isError = state.time.isBlank() || state.time.isEmpty() || state.time.toInt() > 60,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
            }
            SwitchRow(
                text = "벨소리",
                state = state.soundSwitch,
                onClick = { viewModel.soundSwitch() }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "벨소리 선택", color = color, fontSize = 20.sp)
                Icon(
                    painter = painterResource(id = R.drawable.ic_forward),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.clickable { viewModel.showRingtoneDialog() }
                )
            }
            SwitchRow(
                modifier = Modifier.fillMaxWidth(),
                text = "진동 설정",
                state = state.vibrationSwitch,
                onClick = { viewModel.vibrationSwitch() }
            )
            SwitchRow(
                modifier = Modifier.fillMaxWidth(),
                text = "구구단",
                state = state.problemSwitch,
                onClick = { viewModel.problemSwitch() }
            )
            UpDownRow(
                title = "개수",
                text = state.problemCnt.toString(),
                onDownClicked = { viewModel.problemMinus() },
                onUpClicked = { viewModel.problemPlus() }
            )
        }
    }

    if (state.showDialog) {
        ShowDialog(
            text = R.string.save,
            onDismissRequest = { viewModel.onDialogCancel() },
            confirmButton = { viewModel.onConfirmClicked() },
            dismissButton = { viewModel.onDialogCancel() }
        )
    }

    if (state.showRingtoneDialog) {
        Dialog(onDismissRequest = { viewModel.onDismissRingtoneDialog() }) {
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(500.dp),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp)
                ) {
                    Text(
                        text = "벨소리 설정",
                        color = color,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(modifier = Modifier.height(390.dp)) {
                        items(state.ringtoneList) { ringtone ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = state.radioSelected == ringtone.first,
                                    onClick = { viewModel.clickRingtoneItem(ringtone) },
                                )
                                Text(text = ringtone.first)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TextButton(onClick = { viewModel.onDismissRingtoneDialog() }) {
                            Text(text = "취소", color = Color.Blue, fontSize = 15.sp)
                        }
                        TextButton(onClick = { viewModel.onConfirmRingtoneDialog() }) {
                            Text(text = "확인", color = Color.Blue, fontSize = 15.sp)
                        }
                    }
                }

            }
        }
    }
}