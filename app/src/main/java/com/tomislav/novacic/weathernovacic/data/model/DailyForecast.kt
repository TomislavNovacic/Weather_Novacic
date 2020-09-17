package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DailyForecast {
    @SerializedName("city")
    @Expose
    lateinit var city: City

    @SerializedName("cnt")
    @Expose
    lateinit var cnt: String

    @SerializedName("cod")
    @Expose
    lateinit var cod: String

    @SerializedName("message")
    @Expose
    lateinit var message: String

    @SerializedName("list")
    @Expose
    lateinit var list: Array<List>
}