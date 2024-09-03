package com.news.ui.screens.home

import com.news.domain.entity.Article


sealed class HomeViewState {
    object Idle : HomeViewState()
    object Loading : HomeViewState()
    data class Error(val error: String) : HomeViewState()
    data class Result(val data: List<Article>) : HomeViewState()
}