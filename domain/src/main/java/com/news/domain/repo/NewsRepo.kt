package com.news.domain.repo

import com.news.domain.entity.Article
import com.news.domain.entity.News
import com.news.domain.entity.Resource

interface NewsRepo {
    suspend fun getNews(category: String): Resource<News>
    suspend fun saveNews(news: List<Article>)
    suspend fun getLocalNews(): Resource<News>
}