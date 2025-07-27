package com.example.news.data.remote

import com.example.news.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RequestService {

    @GET("everything")
   suspend fun getNews(@Query("q") q:String,@Query("apiKey") apiKey:String = Constants.BASE_URL): Response<com.example.news.data.models.News>
}