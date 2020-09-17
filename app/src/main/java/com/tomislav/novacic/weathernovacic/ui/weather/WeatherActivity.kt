package com.tomislav.novacic.weathernovacic.ui.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.tomislav.novacic.weathernovacic.BuildConfig.API_KEY
import com.tomislav.novacic.weathernovacic.R
import com.tomislav.novacic.weathernovacic.base.BaseActivity
import com.tomislav.novacic.weathernovacic.data.WeatherAdapter
import com.tomislav.novacic.weathernovacic.data.model.CurrentWeather
import com.tomislav.novacic.weathernovacic.data.model.DailyForecast
import com.tomislav.novacic.weathernovacic.data.model.List
import com.tomislav.novacic.weathernovacic.utils.DialogHelper
import com.tomislav.novacic.weathernovacic.utils.PermissionHelper
import com.tomislav.novacic.weathernovacic.utils.Utils
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter


class WeatherActivity : BaseActivity() {

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private var locationRequest: LocationRequest? = null
    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var currentDayList: ArrayList<List>
    private lateinit var firstDayList: ArrayList<List>
    private lateinit var secondDayList: ArrayList<List>
    private lateinit var thirdDayList: ArrayList<List>
    private lateinit var currentDayAdapter: WeatherAdapter
    private lateinit var firstDayAdapter: WeatherAdapter
    private lateinit var secondDayAdapter: WeatherAdapter
    private lateinit var thirdDayAdapter: WeatherAdapter

    companion object {
        private const val LOCATION_PERMISSION_REQ_CODE = 1
        private const val CHECK_ARE_LOCATIONS_ENABLED_REQ_CODE = 2

        fun newInstance(context: Context) = Intent(context, WeatherActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        if (!PermissionHelper().isPermissionGranted(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE)
            return
        } else {
            getUserLocation()
        }
        viewModel.currentWeatherLoading.observe(this) {
            if (it) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        }
        viewModel.dailyForecastLoading.observe(this) {
            if (it) {
                progress2.visibility = View.VISIBLE
            } else {
                progress2.visibility = View.GONE
            }
        }
        viewModel.currentWeather.observe(this) {
            populateLayout(it)
        }
        viewModel.dailyForecast.observe(this) {
            title = "${it.city.name}, ${it.city.country}"
            updateDailyForecasts(it)
        }
        initLayout()
    }

    private fun updateDailyForecasts(it: DailyForecast) {
        val currentDate = ZonedDateTime.now(ZoneId.systemDefault())
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val currentDay = currentDate.format(dateFormatter)
        val firstDay = currentDate.plusDays(1).format(dateFormatter)
        val secondDay = currentDate.plusDays(2).format(dateFormatter)
        val thirdDay = currentDate.plusDays(3).format(dateFormatter)
        currentDayTv.text = getString(R.string.today)
        firstDayTv.text = firstDay
        secondDayTv.text = secondDay
        thirdDayTv.text = thirdDay
        currentDayList.clear()
        firstDayList.clear()
        secondDayList.clear()
        thirdDayList.clear()
        for (dailyForecast in it.list) {
            val instant = Instant.ofEpochSecond(dailyForecast.dt.toLong())
            val dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
            when {
                dateTime.format(dateFormatter) == currentDay -> {
                    currentDayList.add(dailyForecast)
                }
                dateTime.format(dateFormatter) == firstDay -> {
                    firstDayList.add(dailyForecast)
                }
                dateTime.format(dateFormatter) == secondDay -> {
                    secondDayList.add(dailyForecast)
                }
                dateTime.format(dateFormatter) == thirdDay -> {
                    thirdDayList.add(dailyForecast)
                }
            }
        }
        currentDayAdapter.notifyDataSetChanged()
        firstDayAdapter.notifyDataSetChanged()
        secondDayAdapter.notifyDataSetChanged()
        thirdDayAdapter.notifyDataSetChanged()
    }

    private fun initLayout() {
        currentDayList = arrayListOf()
        firstDayList = arrayListOf()
        secondDayList = arrayListOf()
        thirdDayList = arrayListOf()
        currentDayAdapter = WeatherAdapter(this, currentDayList)
        currentDayRecyclerView.adapter = currentDayAdapter
        firstDayAdapter = WeatherAdapter(this, firstDayList)
        firstDayRecyclerView.adapter = firstDayAdapter
        secondDayAdapter = WeatherAdapter(this, secondDayList)
        secondDayRecyclerView.adapter = secondDayAdapter
        thirdDayAdapter = WeatherAdapter(this, thirdDayList)
        thirdDayRecyclerView.adapter = thirdDayAdapter
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQ_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation()
            } else {
                DialogHelper().showYesNoDialog(
                    this,
                    getString(R.string.location_permission_dialog_title),
                    getString(R.string.location_permission_dialog_msg),
                    getString(R.string.location_permission_dialog_yes_btn),
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        null
                    } else {
                        Runnable {
                            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE)
                        }
                    },
                    getString(R.string.location_permission_dialog_no_btn),
                    { finish() })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == CHECK_ARE_LOCATIONS_ENABLED_REQ_CODE) {
                requestLocationUpdates(createLocationCallback())
            }
        } else {
            DialogHelper().showConfirmDialog(this, getString(R.string.location_settings_dialog_title), getString(R.string.location_settings_dialog_msg)) { finish() }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    viewModel.getCurrentWeather(location.latitude, location.longitude, getString(R.string.units_metric), API_KEY)
                    viewModel.getDailyForecast(location.latitude, location.longitude, getString(R.string.units_metric), API_KEY)
                } else {
                    requestLocation()
                }
            }
    }

    private fun requestLocation() {
        locationRequest = LocationRequest.create()?.apply {
            interval = 20000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        if (locationRequest != null) {
            checkAreLocationsEnabled()
        }
    }

    private fun checkAreLocationsEnabled() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            requestLocationUpdates(createLocationCallback())
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(this, CHECK_ARE_LOCATIONS_ENABLED_REQ_CODE)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun createLocationCallback(): LocationCallback {
        return object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.locations[0]
                viewModel.getCurrentWeather(location.latitude, location.longitude, getString(R.string.units_metric), API_KEY)
                viewModel.getDailyForecast(location.latitude, location.longitude, getString(R.string.units_metric), API_KEY)
                fusedLocationClient.removeLocationUpdates(this)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates(locationCallback: LocationCallback) {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun populateLayout(weather: CurrentWeather) {
        val currentDate = ZonedDateTime.now(ZoneId.systemDefault())
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        date.text = currentDate.format(dateFormatter)
        time.text = currentDate.format(timeFormatter)
        temperature.text = Utils.formatTemperature(weather.main.temp)
        maxTemp.text = String.format(getString(R.string.max_temp), Utils.formatTemperature(weather.main.tempMax))
        minTemp.text = String.format(getString(R.string.min_temp), Utils.formatTemperature(weather.main.tempMin))
        wind.text = String.format(getString(R.string.wind_speed), weather.wind.speed)
        Glide.with(this).load(String.format(getString(R.string.weather_icon_url), weather.weather[0].icon)).into(weatherImg)
        weatherType.text = weather.weather[0].main
    }
}