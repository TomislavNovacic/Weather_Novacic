package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Id {
    @SerializedName("kind")
    @Expose
    lateinit var kind: String

    @SerializedName("videoId")
    @Expose
    lateinit var videoId: String
}