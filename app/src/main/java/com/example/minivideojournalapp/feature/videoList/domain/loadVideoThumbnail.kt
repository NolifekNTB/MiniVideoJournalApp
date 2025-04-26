package com.example.minivideojournalapp.feature.videoList.domain

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri

fun loadVideoThumbnail(context: Context, uri: Uri): Bitmap? {
    return try {
        MediaMetadataRetriever().apply {
            setDataSource(context, uri)
        }.frameAtTime
    } catch (e: Exception) {
        null
    }
}