package com.news.ui.screens.home

import com.news.data.entity.Article

sealed class HomeViewState {
    object Idle : HomeViewState()
    object Loading : HomeViewState()
    data class Error(val error: String) : HomeViewState()
    data class Result(val data: List<Article>) : HomeViewState()
}