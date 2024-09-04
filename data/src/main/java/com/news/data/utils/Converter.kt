package com.news.data.utils

import com.news.data.entity.NewsResponse
import com.news.data.entity.Source
import com.news.domain.entity.Article
import com.news.domain.entity.News


fun NewsResponse.toLocalModel(): News = News(
    articles = articles.map {
        Article(
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage,
            category = it.category
        )
    }
)

fun List<com.news.data.entity.Article>.toLocalModel(category: String? = null): List<Article> =
    this.map {
        Article(
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage,
            category = category ?: it.category
        )
    }

fun List<Article>.toDataModel(): List<com.news.data.entity.Article> =
    this.map {
        com.news.data.entity.Article(
            author = it.author ?: "",
            content = it.content ?: "",
            description = it.description ?: "",
            publishedAt = it.publishedAt,
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage ?: "",
            source = Source(),
            category = it.category
        )
    }