package com.example.minivideojournalapp.core.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.minivideojournalapp.AppDatabase
import com.example.minivideojournalapp.feature.videoList.data.local.VideoRecordingDao
import com.example.minivideojournalapp.feature.videoList.data.local.VideoRecordingDaoImpl
import com.example.minivideojournalapp.feature.recording.ui.VideoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        AppDatabase(
            AndroidSqliteDriver(
                AppDatabase.Schema,
                get(),
                "videos.db"
            )
        )
    }
    single { get<AppDatabase>().videoQueries }
    single<VideoRecordingDao> { VideoRecordingDaoImpl(get()) }
    viewModel { VideoViewModel(get()) }
}
