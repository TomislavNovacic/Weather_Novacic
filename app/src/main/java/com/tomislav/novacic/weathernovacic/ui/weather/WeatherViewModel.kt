package com.tomislav.novacic.weathernovacic.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomislav.novacic.weathernovacic.data.model.CurrentWeather
import com.tomislav.novacic.weathernovacic.data.model.DailyForecast
import kotlinx.coroutines.launch

class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {

    private val _currentWeatherLoading = MutableLiveData<Boolean>()
    val currentWeatherLoading: LiveData<Boolean> = _currentWeatherLoading

    private val _dailyForecastLoading = MutableLiveData<Boolean>()
    val dailyForecastLoading: LiveData<Boolean> = _dailyForecastLoading

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    private val _dailyForecast = MutableLiveData<DailyForecast>()
    val dailyForecast: LiveData<DailyForecast> = _dailyForecast

    init {
        _currentWeatherLoading.postValue(false)
        _dailyForecastLoading.postValue(false)
    }

    fun getCurrentWeather(lat: Double, lon: Double, units: String, apiKey: String) {
        _currentWeatherLoading.postValue(true)
        viewModelScope.launch {
            val response = repo.getCurrentWeather(lat, lon, units, apiKey)
            _currentWeather.postValue(response)
            _currentWeatherLoading.postValue(false)
        }
    }

    fun getDailyForecast(lat: Double, lon: Double, units: String, apiKey: String) {
        _dailyForecastLoading.postValue(true)
        viewModelScope.launch {
            val response = repo.getDailyForecast(lat, lon, units, apiKey)
            _dailyForecast.postValue(response)
            _dailyForecastLoading.postValue(false)
        }
    }
}