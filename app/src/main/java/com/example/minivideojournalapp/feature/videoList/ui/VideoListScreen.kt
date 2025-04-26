package com.example.minivideojournalapp.feature.videoList.ui

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.minivideojournalapp.R
import com.example.minivideojournalapp.feature.recording.ui.VideoViewModel
import com.example.minivideojournalapp.feature.videoList.domain.loadVideoThumbnail
import comexampleminivideojournalapp.Video_recordings
import java.util.Date

@Composable
fun VideoListScreen(onVideoClick: (String) -> Unit) {
    val viewModel: VideoViewModel = koinViewModel()
    val videos by viewModel.videos.collectAsState()

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
    val bitmap = loadVideoThumbnail(LocalContext.current, Uri.parse(video.file_path))

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onVideoClick(video.file_path) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(video.description ?: stringResource(R.string.no_description), style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(
                    R.string.video_info,
                    video.duration_ms / 1000,
                    Date(video.timestamp).toString()
                ),
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary
            )
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
            description = "A beautiful sunrise",
            duration_ms = 60000
        ),
        Video_recordings(
            id = 2,
            file_path = "/storage/emulated/0/Android/media/app/video2.mp4",
            timestamp = System.currentTimeMillis() - 3600000L,
            description = "Evening walk notes",
            duration_ms = 3600000
        )
    )

    VideoListScreenInternal(videos = mockVideos) {}
}

