package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Coord {
    @SerializedName("lon")
    @Expose
    lateinit var lon: String

    @SerializedName("lat")
    @Expose
    lateinit var lat: String
}