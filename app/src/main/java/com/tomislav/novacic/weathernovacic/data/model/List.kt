package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class List {
    @SerializedName("dt")
    @Expose
    lateinit var dt: String

    @SerializedName("pop")
    @Expose
    lateinit var pop: String

    @SerializedName("visibility")
    @Expose
    lateinit var visibility: String

    @SerializedName("dt_txt")
    @Expose
    lateinit var dtTxt: String

    @SerializedName("weather")
    @Expose
    lateinit var weather: Array<Weather>

    @SerializedName("main")
    @Expose
    lateinit var main: Main

    @SerializedName("clouds")
    @Expose
    lateinit var clouds: Clouds

    @SerializedName("sys")
    @Expose
    lateinit var sys: Sys

    @SerializedName("wind")
    @Expose
    lateinit var wind: Wind
}