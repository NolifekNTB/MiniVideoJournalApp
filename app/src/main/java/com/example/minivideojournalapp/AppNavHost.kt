package com.example.minivideojournalapp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.minivideojournalapp.feature.videoList.ui.VideoListScreen
import com.example.minivideojournalapp.feature.recording.ui.VideoRecordingScreen
import com.example.minivideojournalapp.feature.videoList.ui.VideoPlayerScreen
import kotlinx.serialization.Serializable

@Serializable
object RecordScreen

@Serializable
object VideoListScreen

@Serializable
data class VideoPlayerScreenArgs(val videoUri: String)

@Composable
fun AppNavHost(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = RecordScreen,
        modifier = Modifier.padding(padding)
    ) {
        composable<RecordScreen> {
            VideoRecordingScreen()
        }
        composable<VideoListScreen> {
            VideoListScreen { selectedUri ->
                navController.navigate(VideoPlayerScreenArgs(videoUri = selectedUri))
            }
        }

        composable<VideoPlayerScreenArgs> {
            val args = it.toRoute<VideoPlayerScreenArgs>()
            VideoPlayerScreen(videoUri = args.videoUri)
        }
    }
}