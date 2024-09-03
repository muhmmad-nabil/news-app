package com.news.data.utils

import com.news.domain.entity.Resource
import retrofit2.Response

fun <T> Response<T>.checkResponse(): Resource<T> {
    return if (this.isSuccessful) Resource.Success(data = this.body()!!)
    else Resource.Error(message = this.code().toString())
}