package com.example.minivideojournalapp.feature.videoList.domain

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri

fun getVideoDuration(context: Context, uri: Uri): Long {
    return try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, uri)
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        retriever.release()
        time?.toLong() ?: 0L
    } catch (e: Exception) {
        0L
    }
}
