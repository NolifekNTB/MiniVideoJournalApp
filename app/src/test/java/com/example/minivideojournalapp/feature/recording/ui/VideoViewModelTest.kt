package com.example.minivideojournalapp.feature.recording.ui

import com.example.minivideojournalapp.feature.videoList.data.local.VideoRecordingDao
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VideoViewModelTest {

    private lateinit var videoDao: VideoRecordingDao
    private lateinit var viewModel: VideoViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        videoDao = mockkClass(VideoRecordingDao::class, relaxUnitFun = true)
        every { videoDao.getAllVideos() } returns flowOf(emptyList())
        viewModel = VideoViewModel(videoDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveVideo should call dao insertVideo`() = runTest(testDispatcher) {
        viewModel.saveVideo("path", "desc", 12345L)

        advanceUntilIdle()

        coVerify {
            videoDao.insertVideo(filePath = "path", timestamp = any(), description = "desc", durationMs = 12345L)
        }
    }
}
