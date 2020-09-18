package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sys {
    @SerializedName("country")
    @Expose
    lateinit var country: String

    @SerializedName("sunrise")
    @Expose
    lateinit var sunrise: String

    @SerializedName("sunset")
    @Expose
    lateinit var sunset: String

    @SerializedName("id")
    @Expose
    lateinit var id: String

    @SerializedName("type")
    @Expose
    lateinit var type: String
}