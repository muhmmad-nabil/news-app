package com.news.data.local

import com.news.data.entity.Article

class LocalDataSourceImpl(private val dao: NewsDao) : LocalDataSource {
    override suspend fun getNews(): List<Article> = dao.getNews()

    override suspend fun setNews(articles: List<Article>) {
        dao.setNews(articles)
    }

}