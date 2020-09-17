package com.tomislav.novacic.weathernovacic.ui.weather

import com.tomislav.novacic.weathernovacic.data.model.CurrentWeather
import com.tomislav.novacic.weathernovacic.data.model.DailyForecast
import com.tomislav.novacic.weathernovacic.data.source.remote.RetrofitService

class WeatherRepository(private val retrofitService: RetrofitService) {

    suspend fun getCurrentWeather(lat: Double, lon: Double, units: String, apiKey: String): CurrentWeather {
        val apiClient = retrofitService.provideRetrofitService()
        return apiClient.getCurrentWeather(lat, lon, units, apiKey)
    }

    suspend fun getDailyForecast(lat: Double, lon: Double, units: String, apiKey: String): DailyForecast {
        val apiClient = retrofitService.provideRetrofitService()
        return apiClient.getDailyForecast(lat, lon, units, apiKey)
    }
}