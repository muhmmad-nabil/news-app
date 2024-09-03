package com.news.data.remote

import com.news.data.entity.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitDataSource {
    @GET("top-headlines/category/{category}/us.json")
    suspend fun getNews(@Path("category") category: String): Response<NewsResponse>
}