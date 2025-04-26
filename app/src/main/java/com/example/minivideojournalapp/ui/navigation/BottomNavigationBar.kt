package com.example.minivideojournalapp.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.minivideojournalapp.RecordScreen
import com.example.minivideojournalapp.VideoListScreen

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentRoute: Any?,
    items: List<BottomNavItem>,
    ) {
    NavigationBar {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    when (item.screen) {
                        is RecordScreen -> navController.navigate(RecordScreen)
                        is VideoListScreen -> navController.navigate(VideoListScreen)
                        else -> {}
                    }
                },
                icon = { Icon(painter = painterResource(item.icon), contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
