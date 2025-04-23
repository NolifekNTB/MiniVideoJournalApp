package com.example.minivideojournalapp

import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun VideoRecordingScreen(viewModel: VideoViewModel = koinViewModel()) {
    val showTitleDialog = remember { mutableStateOf(false) }
    val lastSavedPath = remember { mutableStateOf<String?>(null) }
    val descriptionInput = remember { mutableStateOf("") }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    val videoCapture = remember { MutableStateFlow<VideoCapture<Recorder>?>(null) }
    val recording = remember { mutableStateOf<Recording?>(null) }

    LaunchedEffect(Unit) {
        val cameraProvider = ProcessCameraProvider.getInstance(context).get()

        val preview = Preview.Builder().build().also {
            it.surfaceProvider = previewView.surfaceProvider
        }

        val recorder = Recorder.Builder()
            .setQualitySelector(QualitySelector.from(Quality.HD))
            .build()

        val video = VideoCapture.withOutput(recorder)
        videoCapture.value = video

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview,
            video
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView({ previewView }, modifier = Modifier.weight(1f))

        Button(onClick = {
            val existingRecording = recording.value
            if (existingRecording != null) {
                existingRecording.stop()
                recording.value = null
                return@Button // Exit early until it's fully finalized
            }

            val output = File(context.externalMediaDirs.first(), "${System.currentTimeMillis()}.mp4")
            val outputOptions = FileOutputOptions.Builder(output).build()

            recording.value = videoCapture.value?.output
                ?.prepareRecording(context, outputOptions)
                ?.start(ContextCompat.getMainExecutor(context)) { event ->
                    when (event) {
                        is VideoRecordEvent.Finalize -> {
                            recording.value = null
                            Toast.makeText(context, "Saved to ${output.path}", Toast.LENGTH_SHORT).show()
                            lastSavedPath.value = output.absolutePath
                            showTitleDialog.value = true
                        }
                        is VideoRecordEvent.Start -> {
                            // Optional: notify recording started
                        }
                    }
                }
        }) {
            Text(if (recording.value == null) "Start Recording" else "Stop Recording")
        }
    }

    if (showTitleDialog.value && lastSavedPath.value != null) {
        AlertDialog(
            onDismissRequest = { showTitleDialog.value = false },
            title = { Text("Add a Description") },
            text = {
                TextField(
                    value = descriptionInput.value,
                    onValueChange = { descriptionInput.value = it },
                    placeholder = { Text("Enter an optional description") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.saveVideo(lastSavedPath.value!!, descriptionInput.value.ifBlank { null })
                    showTitleDialog.value = false
                    descriptionInput.value = ""
                    lastSavedPath.value = null
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    viewModel.saveVideo(lastSavedPath.value!!, null)
                    showTitleDialog.value = false
                    descriptionInput.value = ""
                    lastSavedPath.value = null
                }) {
                    Text("Skip")
                }
            }
        )
    }

}
