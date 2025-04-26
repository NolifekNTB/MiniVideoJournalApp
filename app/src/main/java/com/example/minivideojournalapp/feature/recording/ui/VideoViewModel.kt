package com.example.minivideojournalapp.feature.recording.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minivideojournalapp.feature.videoList.data.local.VideoRecordingDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VideoViewModel(
    private val videoDao: VideoRecordingDao
) : ViewModel() {

    val videos = videoDao.getAllVideos()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun saveVideo(
        filePath: String,
        description: String?,
        durationMs: Long
    ) {
        viewModelScope.launch {
            videoDao.insertVideo(
                filePath = filePath,
                timestamp = System.currentTimeMillis(),
                description = description,
                durationMs = durationMs
            )
        }
    }
}
