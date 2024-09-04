package com.news.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.news.data.entity.Article

@Dao
interface NewsDao {
    @Query("SELECT * FROM article")
    suspend fun getNews(): List<Article>

    @Insert
    suspend fun setNews(articles: List<Article>)
}