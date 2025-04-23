package com.example.minivideojournalapp

import androidx.lifecycle.ViewModel
import comexampleminivideojournalapp.VideoQueries
import comexampleminivideojournalapp.Video_recordings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VideoViewModel(
    private val queries: VideoQueries
) : ViewModel() {

    private val _videos = MutableStateFlow<List<Video_recordings>>(emptyList())
    val videos: StateFlow<List<Video_recordings>> = _videos.asStateFlow()

    init {
        loadVideos()
    }

    fun saveVideo(filePath: String, description: String? = null) {
        queries.insertVideo(
            file_path = filePath,
            timestamp = System.currentTimeMillis(),
            description = description
        )
        loadVideos()
    }

    private fun loadVideos() {
        _videos.value = queries.getAllVideos().executeAsList()
    }
}
