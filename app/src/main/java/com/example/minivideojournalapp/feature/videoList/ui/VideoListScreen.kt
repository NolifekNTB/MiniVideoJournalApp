package com.example.minivideojournalapp.feature.videoList.ui

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.tooling.preview.Preview
import com.example.minivideojournalapp.feature.camera.ui.VideoViewModel
import com.example.minivideojournalapp.ui.shared.RequestPermission
import comexampleminivideojournalapp.Video_recordings
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun VideoListScreen(onVideoClick: (String) -> Unit) {
    val viewModel: VideoViewModel = koinViewModel()
    val videos by viewModel.videos.collectAsState()

    RequestPermission(
        permission = listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
        rationaleText = "We need access to your storage to display saved videos.",
        onAllGranted = { /* your logic */ },
    )

    VideoListScreenInternal(videos = videos) {
        onVideoClick(it)
    }
}

@Composable
fun VideoListScreenInternal(
    videos: List<Video_recordings>,
    onVideoClick: (String) -> Unit,
){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(videos) { video ->
            VideoListItem(video) {
                onVideoClick(it)
            }
        }
    }
}

@Composable
fun VideoListItem(
    video: Video_recordings,
    onVideoClick: (String) -> Unit = {}
) {
    Column(modifier = Modifier
        .clickable { onVideoClick(video.file_path) }
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Text("📝 ${video.description ?: "No Description"}")
        Text("📁 ${video.file_path}")
        Text("🕒 ${Date(video.timestamp)}")
        HorizontalDivider()
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

    VideoListScreenInternal(videos = mockVideos) {}
}

