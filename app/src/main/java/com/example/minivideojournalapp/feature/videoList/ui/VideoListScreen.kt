package com.example.minivideojournalapp.feature.videoList.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.tooling.preview.Preview
import com.example.minivideojournalapp.feature.camera.ui.VideoViewModel
import comexampleminivideojournalapp.Video_recordings
import java.util.Date

@Composable
fun VideoListScreen() {
    val viewModel: VideoViewModel = koinViewModel()
    val videos by viewModel.videos.collectAsState()

    VideoListScreenInternal(videos = videos)
}

@Composable
fun VideoListScreenInternal(
    videos: List<Video_recordings>
){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(videos) { video ->
            Column(modifier = Modifier.padding(16.dp)) {
                Text("📁 Path: ${video.file_path}")
                Text("🕒 Saved: ${Date(video.timestamp)}")
                if (!video.description.isNullOrBlank()) {
                    Text("📝 ${video.description}")
                }
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VideoListScreenPreview() {
    val mockVideos = listOf(
        Video_recordings(
            id = 1,
            file_path = "/storage/emulated/0/Android/media/app/video1.mp4",
            timestamp = System.currentTimeMillis(),
            description = "A beautiful sunrise 🌅"
        ),
        Video_recordings(
            id = 2,
            file_path = "/storage/emulated/0/Android/media/app/video2.mp4",
            timestamp = System.currentTimeMillis() - 3600000L,
            description = "Evening walk notes"
        )
    )

    VideoListScreenInternal(videos = mockVideos)
}

