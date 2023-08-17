package com.dev_musashi.wakeup.presentation.navigation

import androidx.compose.foundation.isSystemInDarkTheme
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
    modifier: Modifier = Modifier
) {
    val currentSection = tabs.first { it.route == currentRoute }

    BottomNavigation(
        modifier = modifier
            .height(45.dp)
            .graphicsLayer {
                shape = RectangleShape
                clip = true
            },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = if(isSystemInDarkTheme()) Color.White else Color.Black
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