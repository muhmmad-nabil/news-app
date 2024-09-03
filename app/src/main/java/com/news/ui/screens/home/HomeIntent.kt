package com.news.ui.screens.home

sealed class HomeIntent {
    data class getNews(val category: String) : HomeIntent()
}