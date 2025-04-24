package com.example.minivideojournalapp.feature.camera.domain

import android.content.Context
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.compose.runtime.MutableState
import androidx.core.content.ContextCompat
import java.io.File

fun startOrStopRecording(
    context: Context,
    videoCapture: VideoCapture<Recorder>?,
    recording: MutableState<Recording?>,
    onVideoSaved: (File) -> Unit
) {
    val existingRecording = recording.value
    if (existingRecording != null) {
        existingRecording.stop()
        recording.value = null
        return
    }

    val videoFile = File(context.getExternalFilesDir(null), "video_${System.currentTimeMillis()}.mp4")
    val outputOptions = FileOutputOptions.Builder(videoFile).build()

    recording.value = videoCapture?.output
        ?.prepareRecording(context, outputOptions)
        ?.start(ContextCompat.getMainExecutor(context)) { event ->
            if (event is VideoRecordEvent.Finalize) {
                recording.value = null
                onVideoSaved(videoFile)
            }
        }
}
