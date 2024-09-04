package com.news.data.repo

import com.news.data.local.LocalDataSource
import com.news.data.remote.RetrofitDataSource
import com.news.data.utils.checkResponse
import com.news.data.utils.toDataModel
import com.news.data.utils.toLocalModel
import com.news.domain.entity.Article
import com.news.domain.entity.News
import com.news.domain.entity.Resource
import com.news.domain.repo.NewsRepo

class NewsRepoImpl(
    private val remoteDataSource: RetrofitDataSource,
    private val localDataSource: LocalDataSource
) : NewsRepo {
    override suspend fun getNews(category: String): Resource<News> {
        return when (val result = remoteDataSource.getNews(category).checkResponse()) {
            is Resource.Success -> {
                try {
                    saveNews(result.data?.articles?.toLocalModel(category) ?: emptyList())
                    Resource.Success(result.data?.toLocalModel()!!)
                } catch (ex: Exception) {
                    Resource.Error(ex.message.toString())
                }
            }

            is Resource.Error -> Resource.Error(result.message)
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun saveNews(news: List<Article>) {
        localDataSource.setNews(news.toDataModel())
    }

    override suspend fun getLocalNews(): Resource<News> =
        try {
            Resource.Success(News(localDataSource.getNews().toLocalModel()))
        } catch (ex: Exception) {
            Resource.Error(ex.message.toString())
        }
}