package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CurrentWeather {
    @SerializedName("visibility")
    @Expose
    lateinit var visibility: String

    @SerializedName("timezone")
    @Expose
    lateinit var timezone: String

    @SerializedName("main")
    @Expose
    lateinit var main: Main

    @SerializedName("clouds")
    @Expose
    lateinit var clouds: Clouds

    @SerializedName("sys")
    @Expose
    lateinit var sys: Sys

    @SerializedName("dt")
    @Expose
    lateinit var dt: String

    @SerializedName("coord")
    @Expose
    lateinit var coord: Coord

    @SerializedName("weather")
    @Expose
    lateinit var weather: Array<Weather>

    @SerializedName("name")
    @Expose
    lateinit var name: String

    @SerializedName("cod")
    @Expose
    lateinit var cod: String

    @SerializedName("id")
    @Expose
    lateinit var id: String

    @SerializedName("base")
    @Expose
    lateinit var base: String

    @SerializedName("wind")
    @Expose
    lateinit var wind: Wind
}