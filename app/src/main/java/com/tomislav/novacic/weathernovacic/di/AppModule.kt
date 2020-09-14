package com.tomislav.novacic.weathernovacic.di

import com.tomislav.novacic.weathernovacic.WeatherRepository
import com.tomislav.novacic.weathernovacic.WeatherViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { WeatherRepository() }
    viewModel { WeatherViewModel(get()) }
}