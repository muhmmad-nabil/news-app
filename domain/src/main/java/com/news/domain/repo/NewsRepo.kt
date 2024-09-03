package com.news.domain.repo

import com.news.domain.entity.News
import com.news.domain.entity.Resource

interface NewsRepo {
    suspend fun getNews(category: String): Resource<News>
}