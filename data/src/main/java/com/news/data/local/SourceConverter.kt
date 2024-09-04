package com.news.data.local

import androidx.room.TypeConverter
import com.news.data.entity.Source

class SourceConverter {
    @TypeConverter
    fun fromSource(source: Source?): String? {
        return source?.toString()
    }

    @TypeConverter
    fun toSource(source: String?): Source? {
        return source?.let { Source(it) }
    }
}