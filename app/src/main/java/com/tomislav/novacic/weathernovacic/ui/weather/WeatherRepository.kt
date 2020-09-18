package com.tomislav.novacic.weathernovacic.ui.weather

import com.tomislav.novacic.weathernovacic.data.model.CurrentWeather
import com.tomislav.novacic.weathernovacic.data.model.DailyForecast
import com.tomislav.novacic.weathernovacic.data.source.remote.RetrofitService

class WeatherRepository(private val retrofitService: RetrofitService) {

    suspend fun getCurrentWeather(lat: Double, lon: Double, units: String, apiKey: String): CurrentWeather {
        val apiClient = retrofitService.provideRetrofitService()
        return apiClient.getCurrentWeather(lat, lon, units, apiKey)
    }

    suspend fun getCurrentWeatherByName(cityName: String, units: String, apiKey: String): CurrentWeather? {
        val apiClient = retrofitService.provideRetrofitService()
        return try {
            apiClient.getCurrentWeatherByName(cityName, units, apiKey)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getDailyForecast(lat: Double, lon: Double, units: String, apiKey: String): DailyForecast {
        val apiClient = retrofitService.provideRetrofitService()
        return apiClient.getDailyForecast(lat, lon, units, apiKey)
    }

    suspend fun getDailyForecastByName(cityName: String, units: String, apiKey: String): DailyForecast? {
        val apiClient = retrofitService.provideRetrofitService()
        return try {
            apiClient.getDailyForecastByName(cityName, units, apiKey)
        } catch (e: Exception) {
            null
        }
    }

    fun getVideoId(apiKey: String, query: String, maxResults: Int, type: String): String {
        val apiClient = retrofitService.provideYoutubeRetrofitService()
        return try {
            val response = apiClient.getVideoId(apiKey, query, maxResults, type)
            return response.items[0].id.videoId
        } catch (e: Exception) {
            // I was short on time :)
            "4OWTkWuMiLg"
        }
    }
}