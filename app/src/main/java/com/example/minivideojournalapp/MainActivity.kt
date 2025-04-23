package com.example.minivideojournalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.minivideojournalapp.ui.theme.MiniVideoJournalAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CameraScreen()
            VideoRecordingScreen()
        }
    }
}

@Composable
fun CameraScreen() {
    val text = remember { mutableStateOf("") }

    CameraPermissionHandler(
        onPermissionGranted = {
            // ✅ Start using the camera
            text.value = "Camera permission granted! Ready to shoot."
        },
        onPermissionDenied = {
            // 🚫 Show UI explaining what to do
            text.value = "Permission denied. Please enable it in settings."
        }
    )
    Text(text = text.value)
}


