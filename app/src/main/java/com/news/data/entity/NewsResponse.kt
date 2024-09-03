package com.news.data.entity

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)