package com.news.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.news.data.local.SourceConverter

@Entity
data class Article(
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
    @TypeConverters(SourceConverter::class)
    val source: Source,
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey var url: String,
    @ColumnInfo(name = "urlToImage") var urlToImage: String,
    @ColumnInfo(name = "category") val category: String,
)