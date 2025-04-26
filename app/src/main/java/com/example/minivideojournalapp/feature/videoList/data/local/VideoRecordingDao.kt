package com.example.minivideojournalapp.feature.videoList.data.local

import comexampleminivideojournalapp.Video_recordings
import kotlinx.coroutines.flow.Flow

interface VideoRecordingDao {
    fun getAllVideos(): Flow<List<Video_recordings>>
    suspend fun insertVideo(
        filePath: String,
        timestamp: Long,
        description: String?,
        durationMs: Long
    )
}