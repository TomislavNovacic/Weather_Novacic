package com.tomislav.novacic.weathernovacic.data.source.remote

import com.tomislav.novacic.weathernovacic.data.model.CurrentWeather
import com.tomislav.novacic.weathernovacic.data.model.DailyForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): CurrentWeather

    @GET("2.5/weather")
    suspend fun getCurrentWeatherByName(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): CurrentWeather

    @GET("2.5/forecast")
    suspend fun getDailyForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): DailyForecast

    @GET("2.5/forecast")
    suspend fun getDailyForecastByName(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): DailyForecast
}