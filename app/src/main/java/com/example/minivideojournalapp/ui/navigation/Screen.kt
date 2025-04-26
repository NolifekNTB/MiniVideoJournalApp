package com.example.minivideojournalapp.ui.navigation

import com.example.minivideojournalapp.R
import com.example.minivideojournalapp.RecordScreen
import com.example.minivideojournalapp.VideoListScreen
import kotlinx.serialization.Serializable

data class BottomNavItem(
    val route: String,
    val screen: @Serializable Any,
    val label: String,
    val icon: Int
)

val bottomNavItems = listOf(
    BottomNavItem("Record",RecordScreen,"Record", R.drawable.camera),
    BottomNavItem("List", VideoListScreen,"List", R.drawable.list)
)