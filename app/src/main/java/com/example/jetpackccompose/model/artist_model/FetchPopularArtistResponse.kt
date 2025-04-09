package com.example.jetpackccompose.model.artist_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FetchPopularArtistResponse {

    @SerializedName("headers")
    @Expose
     val headers: PopularArtistHeaders? = null

    @SerializedName("results")
    @Expose
     val results: List<PopularArtistResult>? = null
}