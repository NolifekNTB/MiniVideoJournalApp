package com.example.minivideojournalapp

import android.app.Application
import com.example.minivideojournalapp.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MiniVideoJournalApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MiniVideoJournalApp)
            modules(appModule)
        }
    }
}
