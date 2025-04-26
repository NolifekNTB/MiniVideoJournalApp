package com.example.minivideojournalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.minivideojournalapp.ui.navigation.BottomNavigation
import com.example.minivideojournalapp.ui.theme.MiniVideoJournalAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniVideoJournalAppTheme {
                BottomNavigation()
            }
        }
    }
}


