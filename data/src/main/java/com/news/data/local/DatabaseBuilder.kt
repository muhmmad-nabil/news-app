package com.news.data.local

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE: NewsDatabase? = null
    fun getInstance(context: Context): NewsDatabase {
        if (INSTANCE == null) {
            synchronized(NewsDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) = Room.databaseBuilder(
        context.applicationContext,
        NewsDatabase::class.java,
        "news"
    ).build()
}