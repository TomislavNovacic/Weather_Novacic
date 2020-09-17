package com.tomislav.novacic.weathernovacic.di

import com.tomislav.novacic.weathernovacic.data.source.remote.RetrofitService
import com.tomislav.novacic.weathernovacic.ui.weather.WeatherRepository
import com.tomislav.novacic.weathernovacic.ui.weather.WeatherViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitService() }
    single { WeatherRepository(get()) }
    viewModel { WeatherViewModel(get()) }
}