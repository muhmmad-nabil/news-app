package com.news.data.local

import com.news.data.entity.Article

interface LocalDataSource {
    suspend fun getNews(): List<Article>
    suspend fun setNews(articles: List<Article>)

}