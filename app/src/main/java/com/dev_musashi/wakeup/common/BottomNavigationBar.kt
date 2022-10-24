package com.dev_musashi.wakeup.common

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    tabs: List<BottomBarScreen>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    modifier: Modifier
) {
    val currentSection = tabs.first { it.route == currentRoute }

    BottomNavigation(
        modifier = modifier
            .height(45.dp)
            .graphicsLayer {
                shape = RectangleShape
                clip = true
            },
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        tabs.forEach { section ->
            val selected = section == currentSection
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = section.icon,
                        contentDescription = null
                    )
                },
                label = null,
                selected = selected,
                unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                onClick = { navigateToRoute(section.route) },
            )
        }
    }
}