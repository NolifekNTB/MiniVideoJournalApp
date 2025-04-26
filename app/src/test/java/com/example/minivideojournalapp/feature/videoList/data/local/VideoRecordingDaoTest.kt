package com.example.minivideojournalapp.feature.videoList.data.local

import comexampleminivideojournalapp.Video_recordings
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class VideoRecordingDaoTest {

    private lateinit var dao: VideoRecordingDao

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
    }

    @Test
    fun `insertVideo should be called with correct parameters`() = runTest {
        val filePath = "path/to/video.mp4"
        val timestamp = System.currentTimeMillis()
        val description = "Test Video"
        val durationMs = 60000L

        dao.insertVideo(filePath, timestamp, description, durationMs)

        coVerify {
            dao.insertVideo(
                filePath = filePath,
                timestamp = timestamp,
                description = description,
                durationMs = durationMs
            )
        }
    }

    @Test
    fun `getAllVideos should return list of videos`() = runTest {
        val fakeList = listOf(
            Video_recordings(
                id = 1,
                file_path = "path/to/video.mp4",
                timestamp = System.currentTimeMillis(),
                description = "Test description",
                duration_ms = 60000L
            )
        )

        every { dao.getAllVideos() } returns flowOf(fakeList)

        val result = dao.getAllVideos()

        result.collect { list ->
            assertEquals(fakeList, list)
        }
    }
}
