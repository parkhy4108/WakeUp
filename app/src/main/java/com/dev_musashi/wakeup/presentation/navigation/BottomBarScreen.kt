package com.dev_musashi.wakeup.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector
) {
    object OnOff : BottomBarScreen(
        route = "OnOff",
        icon = Icons.Default.Home
    )
    object Setting : BottomBarScreen(
        route = "Setting",
        icon = Icons.Default.Settings
    )

}
