package com.news.data.entity

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    var url: String,
    var urlToImage: String
)