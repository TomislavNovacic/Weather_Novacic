package com.tomislav.novacic.weathernovacic.utils

import kotlin.math.roundToInt

class Utils {

    companion object {
        fun formatTemperature(temp: String): String {
            return temp.toDouble().roundToInt().toString() + "°C"
        }
    }
}