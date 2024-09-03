package com.news.domain.usecase

import com.news.domain.entity.News
import com.news.domain.entity.Resource
import com.news.domain.repo.NewsRepo

class GetNewsUseCase(private val repo: NewsRepo) {
    suspend operator fun invoke(category: String): Resource<News> = repo.getNews(category)
}