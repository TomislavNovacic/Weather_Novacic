package com.tomislav.novacic.weathernovacic

import android.app.Application
import com.tomislav.novacic.weathernovacic.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApplication)
            modules(appModule)
        }
    }
}