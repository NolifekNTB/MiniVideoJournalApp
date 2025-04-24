package com.example.minivideojournalapp.core.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.minivideojournalapp.AppDatabase
import com.example.minivideojournalapp.feature.camera.ui.VideoViewModel
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

    viewModel { VideoViewModel(get()) }
}
