package com.example.minivideojournalapp.feature.recording.domain

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File

fun saveVideoToGallery(context: Context, videoFile: File): String {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, videoFile.name)
        put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MOVIES)
        } else {
            put(MediaStore.MediaColumns.DATA, videoFile.absolutePath)
        }
    }

    val resolver = context.contentResolver
    val mediaUri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        mediaUri?.let {
            resolver.openOutputStream(it)?.use { output ->
                videoFile.inputStream().copyTo(output)
            }
        }
    } else {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(videoFile.absolutePath),
            arrayOf("video/mp4"),
            null
        )
    }

    return mediaUri?.toString() ?: videoFile.absolutePath
}
