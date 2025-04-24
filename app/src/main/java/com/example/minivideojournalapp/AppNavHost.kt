package com.example.minivideojournalapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.minivideojournalapp.feature.videoList.ui.VideoListScreen
import com.example.minivideojournalapp.feature.camera.ui.VideoRecordingScreen
import com.example.minivideojournalapp.feature.videoPlayer.VideoPlayerScreen
import kotlinx.serialization.Serializable

@Serializable
object RecordScreen

@Serializable
object VideoListScreen

@Serializable
data class VideoPlayerScreenArgs(val videoUri: String)

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RecordScreen
    ) {
        composable<RecordScreen> {
            VideoRecordingScreen(
                onNavigateToVideos = {
                    navController.navigate(VideoListScreen)
                }
            )
        }
        composable<VideoListScreen> {
            VideoListScreen() { selectedUri ->
                navController.navigate(VideoPlayerScreenArgs(videoUri = selectedUri))
            }
        }

        composable<VideoPlayerScreenArgs> {
            val args = it.toRoute<VideoPlayerScreenArgs>()
            VideoPlayerScreen(videoUri = args.videoUri)
        }
    }
}