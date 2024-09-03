package com.news.data.utils

import com.news.data.entity.NewsResponse
import com.news.domain.entity.Article
import com.news.domain.entity.News

fun NewsResponse.toLocalModel(): News = News(
    articles = articles.map {
        Article(
            author = it.author ?: "",
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage

        )
    }
)