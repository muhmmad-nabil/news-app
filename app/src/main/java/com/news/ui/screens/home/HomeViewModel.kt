package com.news.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"

class HomeViewModel : ViewModel() {
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
            val response =
                RetrofitClient.retrofitDataSource("https://saurav.tech/NewsAPI/").getNews(category)
            if (response.isSuccessful) {
                try {
                    _viewState.emit(HomeViewState.Result(response.body()?.articles ?: emptyList()))
                } catch (ex: Exception) {
                    _viewState.emit(HomeViewState.Error(ex.message.toString()))
                }
            } else {
                _viewState.emit(HomeViewState.Error(response.body().toString()))
            }
        }
    }
}