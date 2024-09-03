package com.news.data.repo

import com.news.data.remote.RetrofitDataSource
import com.news.data.utils.checkResponse
import com.news.data.utils.toLocalModel
import com.news.domain.entity.News
import com.news.domain.entity.Resource
import com.news.domain.repo.NewsRepo

class NewsRepoImpl(
    private val remoteDataSource: RetrofitDataSource,
    // private val localDataSource: LocalDataSource
) : NewsRepo {
    override suspend fun getNews(category: String): Resource<News> {
        return when (val result = remoteDataSource.getNews(category).checkResponse()) {
            is Resource.Success -> {
                try {
                    Resource.Success(result.data?.toLocalModel()!!)
                } catch (ex: Exception) {
                    Resource.Error(ex.message.toString())
                }
            }
            is Resource.Error -> Resource.Error(result.message)
            is Resource.Loading -> Resource.Loading()
        }
    }
}