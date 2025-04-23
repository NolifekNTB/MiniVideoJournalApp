package com.example.minivideojournalapp

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

object DatabaseProvider {
    private var db: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (db == null) {
            db = AppDatabase(
                AndroidSqliteDriver(AppDatabase.Schema, context, "videos.db")
            )
        }
        return db!!
    }
}

fun saveVideoToDatabase(context: Context, filePath: String, title: String? = null) {
    val db = DatabaseProvider.getDatabase(context)
    val timestamp = System.currentTimeMillis()
    db.videoQueries.insertVideo(filePath, timestamp, title)
}

