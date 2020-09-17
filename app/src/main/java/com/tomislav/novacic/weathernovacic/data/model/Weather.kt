package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Weather {
    @SerializedName("icon")
    @Expose
    lateinit var icon: String

    @SerializedName("description")
    @Expose
    lateinit var description: String

    @SerializedName("main")
    @Expose
    lateinit var main: String

    @SerializedName("id")
    @Expose
    lateinit var id: String
}