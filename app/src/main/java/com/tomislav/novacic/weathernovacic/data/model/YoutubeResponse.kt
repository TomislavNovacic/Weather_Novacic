package com.tomislav.novacic.weathernovacic.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class YoutubeResponse {
    @SerializedName("regionCode")
    @Expose
    lateinit var regionCode: String

    @SerializedName("kind")
    @Expose
    lateinit var kind: String

    @SerializedName("nextPageToken")
    @Expose
    lateinit var nextPageToken: String

    @SerializedName("pageInfo")
    @Expose
    lateinit var pageInfo: PageInfo

    @SerializedName("etag")
    @Expose
    lateinit var etag: String

    @SerializedName("items")
    @Expose
    lateinit var items: Array<Items>
}