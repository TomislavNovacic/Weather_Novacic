package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Wind {
    @SerializedName("deg")
    @Expose
    lateinit var deg: String

    @SerializedName("speed")
    @Expose
    lateinit var speed: String
}