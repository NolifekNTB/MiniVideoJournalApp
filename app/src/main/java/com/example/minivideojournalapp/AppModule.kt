package com.example.minivideojournalapp

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
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
