package com.news.domain.usecase

import com.news.domain.entity.News
import com.news.domain.entity.Resource
import com.news.domain.repo.NewsRepo

class GetNewsUseCase(private val repo: NewsRepo) {
    suspend operator fun invoke(isInternetWorking: Boolean, category: String): Resource<News> {
        return if (isInternetWorking) repo.getNews(category)
        else repo.getLocalNews()
    }
}