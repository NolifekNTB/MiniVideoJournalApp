package com.example.minivideojournalapp.feature.camera.ui

import android.Manifest
import android.os.Build
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.camera.video.Recording
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.viewinterop.AndroidView
import com.example.minivideojournalapp.feature.camera.domain.saveVideoToGallery
import com.example.minivideojournalapp.feature.camera.domain.startOrStopRecording
import com.example.minivideojournalapp.feature.camera.domain.setupCamera
import com.example.minivideojournalapp.ui.shared.RequestPermission
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoRecordingScreen(onNavigateToVideos: () -> Unit){
    val viewModel: VideoViewModel = koinViewModel()

    VideoRecordingScreenInternal(
        onNavigateToVideos = onNavigateToVideos,
        saveVideo = viewModel::saveVideo
    )
}

@Composable
fun VideoRecordingScreenInternal(
    onNavigateToVideos: () -> Unit,
    saveVideo: (filePath: String, description: String?) -> Unit,
){
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    val videoCapture = remember { mutableStateOf<VideoCapture<Recorder>?>(null) }
    val recording = remember { mutableStateOf<Recording?>(null) }

    val showDescriptionDialog = remember { mutableStateOf(false) }
    val descriptionInput = remember { mutableStateOf(TextFieldValue("")) }
    val lastSavedPath = remember { mutableStateOf<String?>(null) }

    val requiredPermissions = remember {
        mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        ).apply {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                    add(Manifest.permission.READ_MEDIA_VIDEO)
                }
                Build.VERSION.SDK_INT in 29..32 -> {
                    add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                else -> {
                    add(Manifest.permission.READ_EXTERNAL_STORAGE)
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    }

    RequestPermission(
        permission = requiredPermissions,
        rationaleText = "This app needs camera access to record videos.",
        onAllGranted = {
            setupCamera(previewView, videoCapture, lifecycleOwner, context)
        }
    )


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView({ previewView }, modifier = Modifier.weight(0.9f))

        Row(modifier = Modifier.weight(0.1f)) {
            Button(onClick = {
                startOrStopRecording(context, videoCapture.value, recording) { file ->
                    val uri = saveVideoToGallery(context, file)
                    lastSavedPath.value = uri
                    showDescriptionDialog.value = true
                }
            }) {
                Text(if (recording.value == null) "Start Recording" else "Stop Recording")
            }

            Button(onClick = onNavigateToVideos) {
                Text("View Videos")
            }
        }
    }

    if (showDescriptionDialog.value && lastSavedPath.value != null) {
        AlertDialog(
            onDismissRequest = { showDescriptionDialog.value = false },
            title = { Text("Add Description") },
            text = {
                TextField(
                    value = descriptionInput.value,
                    onValueChange = { descriptionInput.value = it },
                    placeholder = { Text("Optional description") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    saveVideo(lastSavedPath.value!!, descriptionInput.value.text.ifBlank { null })
                    showDescriptionDialog.value = false
                    descriptionInput.value = TextFieldValue("")
                    lastSavedPath.value = null
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    saveVideo(lastSavedPath.value!!, null)
                    showDescriptionDialog.value = false
                    descriptionInput.value = TextFieldValue("")
                    lastSavedPath.value = null
                }) {
                    Text("Skip")
                }
            }
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun VideoRecordingScreenPreview() {
    VideoRecordingScreenInternal(
        onNavigateToVideos = {},
        saveVideo = { _, _ -> }
    )
}

