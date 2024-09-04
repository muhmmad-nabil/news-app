package com.news.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.App.Companion.isInternetConnected
import com.news.domain.entity.Resource
import com.news.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: GetNewsUseCase) : ViewModel() {
    val categories = listOf(
        "business",
        "entertainment",
        "general",
        "health",
        "science",
        "sports",
        "technology",
    )
    val intentChannel = Channel<HomeIntent>(Channel.UNLIMITED)
    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Idle)
    val state: StateFlow<HomeViewState> get() = _viewState

    init {
        processIntent()
        getNews(categories[0])
    }

    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is HomeIntent.getNews -> {
                        _viewState.emit(HomeViewState.Loading)
                        getNews(it.category)
                    }
                }
            }
        }
    }

    private fun getNews(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase(isInternetConnected, category).apply {
                when (this) {
                    is Resource.Success -> _viewState.emit(
                        HomeViewState.Result(
                            data?.articles ?: emptyList()
                        )
                    )

                    is Resource.Error -> _viewState.emit(HomeViewState.Error(message ?: ""))
                    is Resource.Loading -> {
                        _viewState.emit(HomeViewState.Loading)
                    }
                }
            }
        }
    }
}