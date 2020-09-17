package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class City {
    @SerializedName("country")
    @Expose
    lateinit var country: String

    @SerializedName("coord")
    @Expose
    lateinit var coord: Coord

    @SerializedName("sunrise")
    @Expose
    lateinit var sunrise: String

    @SerializedName("timezone")
    @Expose
    lateinit var timezone: String

    @SerializedName("sunset")
    @Expose
    lateinit var sunset: String

    @SerializedName("name")
    @Expose
    lateinit var name: String

    @SerializedName("id")
    @Expose
    lateinit var id: String

    @SerializedName("population")
    @Expose
    lateinit var population: String
}