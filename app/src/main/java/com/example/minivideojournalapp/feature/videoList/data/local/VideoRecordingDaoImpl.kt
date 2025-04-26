package com.example.minivideojournalapp.feature.videoList.data.local

import comexampleminivideojournalapp.VideoQueries
import comexampleminivideojournalapp.Video_recordings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class VideoRecordingDaoImpl(
    private val queries: VideoQueries
) : VideoRecordingDao {

    override fun getAllVideos(): Flow<List<Video_recordings>> = flow {
        emit(queries.getAllVideos().executeAsList())
    }

    override suspend fun insertVideo(
        filePath: String,
        timestamp: Long,
        description: String?,
        durationMs: Long
    ) {
        queries.insertVideo(
            file_path = filePath,
            timestamp = timestamp,
            description = description,
            duration_ms = durationMs
        )
    }
}