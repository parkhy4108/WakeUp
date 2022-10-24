package com.dev_musashi.wakeup.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.wakeup.R
import com.dev_musashi.wakeup.util.ShowDialog

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val state by viewModel.state
    LaunchedEffect(Unit) {
        viewModel.initSetting()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Setting", modifier = Modifier
                    .padding(5.dp), fontSize = 25.sp, color = Color.White
            )
            TextButton(
                onClick = {
                    viewModel.showDialog()
                }
            ) {
                Text(text = "저장", color = Color.White)
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.Gray,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .padding(7.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "시간(최대 간격: 5분 ,최소: 1분)",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.timeMinus() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(text = state.time.toString(), color = Color.White)

                    IconButton(
                        onClick = { viewModel.timePlus() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_forward),
                            contentDescription = null,
                            tint = Color.White
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            BigTitle(
                modifier = Modifier.fillMaxWidth(),
                text = "소리",
                state = state.soundSwitch,
                onClick = { viewModel.soundSwitch() }
            )

            if (state.soundSwitch) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "벨소리 설정", color = Color.White)
                        TextButton(
                            onClick = { viewModel.showRingtoneDialog() }
                        ) {
                            Text(text = "선택", color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(state.ringtoneTitle, color = Color.White, fontSize = 10.sp)
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            BigTitle(
                modifier = Modifier.fillMaxWidth(),
                text = "진동",
                state = state.vibrationSwitch,
                onClick = { viewModel.vibrationSwitch() }
            )
            Spacer(modifier = Modifier.height(20.dp))

            BigTitle(
                modifier = Modifier.fillMaxWidth(),
                text = "구구단",
                state = state.problemSwitch,
                onClick = { viewModel.problemSwitch() }
            )
            if (state.problemSwitch) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "구구단 문제(최대: 5문제, 최소: 1문제)",
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { viewModel.problemMinus() }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Text(text = state.problemCnt.toString(), color = Color.White)

                        IconButton(
                            onClick = { viewModel.problemPlus() }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_forward),
                                contentDescription = null,
                                tint = Color.White
                            )

                        }
                    }
                }
            }

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
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp)
                ) {
                    Text(
                        text = "벨소리 설정",
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        modifier = Modifier
                            .height(390.dp)
                    ) {
                        items(state.ringtoneList) { ringtone ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = state.radioSelected == ringtone.first,
                                    onClick = {
                                        viewModel.clickRingtoneItem(ringtone)
                                    },
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

@Composable
fun BigTitle(
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
            fontSize = 20.sp,
            color = Color.White
        )
        Switch(
            checked = state,
            onCheckedChange = { onClick() },
            colors = SwitchDefaults.colors(Color.White)
        )
    }
}