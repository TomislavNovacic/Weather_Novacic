package com.tomislav.novacic.weathernovacic

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.tomislav.novacic.weathernovacic.base.BaseActivity
import com.tomislav.novacic.weathernovacic.utils.DialogHelper
import com.tomislav.novacic.weathernovacic.utils.PermissionHelper
import kotlinx.android.synthetic.main.activity_weather.*
import java.util.*


class WeatherActivity : BaseActivity() {

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private var locationRequest: LocationRequest? = null

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
                    cityName.text = getCityFromLocation(location)
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
                cityName.text = getCityFromLocation(location)
                fusedLocationClient.removeLocationUpdates(this)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates(locationCallback: LocationCallback) {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun getCityFromLocation(location: Location): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        val cityName = addresses[0].locality
        val countryName = addresses[0].countryName
        return "$cityName, $countryName"
    }
}