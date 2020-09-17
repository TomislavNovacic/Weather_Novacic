package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Items {
    @SerializedName("kind")
    @Expose
    lateinit var kind: String

    @SerializedName("etag")
    @Expose
    lateinit var etag: String

    @SerializedName("id")
    @Expose
    lateinit var id: Id
}