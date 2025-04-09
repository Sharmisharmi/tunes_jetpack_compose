package com.example.jetpackccompose.model.playlist_mode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FetchTopPlayListResponse {


    @SerializedName("headers")
    @Expose
     val headers: TopPlayListHeaders? = null

    @SerializedName("results")
    @Expose
     val results: List<TopPlayListResult>? = null
}