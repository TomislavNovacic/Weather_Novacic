package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Main {
    @SerializedName("temp")
    @Expose
    lateinit var temp: String

    @SerializedName("temp_min")
    @Expose
    lateinit var tempMin: String

    @SerializedName("humidity")
    @Expose
    lateinit var humidity: String

    @SerializedName("pressure")
    @Expose
    lateinit var pressure: String

    @SerializedName("feels_like")
    @Expose
    lateinit var feelsLike: String

    @SerializedName("temp_max")
    @Expose
    lateinit var tempMax: String
}