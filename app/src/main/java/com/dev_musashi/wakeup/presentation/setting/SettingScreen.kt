package com.dev_musashi.wakeup.presentation.setting

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
            .padding(25.dp, 20.dp),
    ) {
        CustomTopBar(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.showDialog() }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.padding(3.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Interval",
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        tint = Color(0xFFB8B8B8),
                        modifier = Modifier.clickable {  viewModel.timeMinus()  },
                    )
                    Text(text = state.time.toString(), color = Color.White)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_forward),
                        contentDescription = null,
                        tint = Color(0xFFB8B8B8),
                        modifier = Modifier.clickable { viewModel.timePlus() }
                    )
                }
            }
            MenuItem(
                modifier = Modifier.fillMaxWidth(),
                text = "Sound",
                state = state.soundSwitch,
                onClick = { viewModel.soundSwitch() }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Select Ringtone", color = Color.White , fontWeight = FontWeight.Light)
                Icon(
                    painter = painterResource(id = R.drawable.ic_forward),
                    contentDescription = null,
                    tint = Color(0xFFB8B8B8),
                    modifier = Modifier.clickable { viewModel.showRingtoneDialog() }
                )
            }
            MenuItem(
                modifier = Modifier.fillMaxWidth(),
                text = "Vibration",
                state = state.vibrationSwitch,
                onClick = { viewModel.vibrationSwitch() }
            )
            MenuItem(
                modifier = Modifier.fillMaxWidth(),
                text = "구구단",
                state = state.problemSwitch,
                onClick = { viewModel.problemSwitch() }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "개수",
                    fontSize = 15.sp,
                    color = Color.White
                )
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        tint = Color(0xFFB8B8B8),
                        modifier = Modifier.clickable { viewModel.problemMinus() }
                    )
                    Text(text = state.problemCnt.toString(), color = Color.White)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_forward),
                        contentDescription = null,
                        tint = Color(0xFFB8B8B8),
                        modifier = Modifier.clickable {  viewModel.problemPlus()  }
                    )
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