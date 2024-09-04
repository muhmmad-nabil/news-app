package com.news.di

import com.news.data.local.DatabaseBuilder
import com.news.data.local.LocalDataSource
import com.news.data.local.LocalDataSourceImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import com.news.data.remote.RetrofitClient
import com.news.data.remote.RetrofitDataSource
import com.news.data.repo.NewsRepoImpl
import com.news.domain.repo.NewsRepo
import com.news.domain.usecase.GetNewsUseCase
import com.news.ui.screens.home.HomeViewModel
import org.koin.dsl.module

val repoModule = module {
    single<NewsRepo> { NewsRepoImpl(get(), get()) }
}

val dataSourceModule = module {
    single<LocalDataSource> {
        LocalDataSourceImpl(DatabaseBuilder.getInstance(get()).newsDao())
    }

    single<RetrofitDataSource> {
        RetrofitClient.retrofitDataSource("https://saurav.tech/NewsAPI/")
    }
}

val useCaseModule = module {
    single<GetNewsUseCase> { GetNewsUseCase(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}