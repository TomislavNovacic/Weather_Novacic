package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PageInfo {
    @SerializedName("totalResults")
    @Expose
    lateinit var totalResults: String

    @SerializedName("resultsPerPage")
    @Expose
    lateinit var resultsPerPage: String
}