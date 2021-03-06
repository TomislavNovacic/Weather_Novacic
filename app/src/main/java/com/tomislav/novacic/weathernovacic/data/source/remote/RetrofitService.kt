package com.tomislav.novacic.weathernovacic.data.source.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/data/"
private const val YOUTUBE_BASE_URL = "https://www.googleapis.com/"

class RetrofitService {

    fun provideRetrofitService(): ApiClient {
        val okHttpClient by lazy {
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiClient::class.java)
    }

    fun provideYoutubeRetrofitService(): ApiClient {
        val okHttpClient by lazy {
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
        }
        return Retrofit.Builder()
            .baseUrl(YOUTUBE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiClient::class.java)
    }
}