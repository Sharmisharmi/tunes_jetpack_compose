package com.example.jetpackccompose.model.artist_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ArtistSongsListResponse {

    @SerializedName("headers")
    @Expose
     val headers: ArtistSongsListHeaders? = null

    @SerializedName("results")
    @Expose
     val results: List<ArtistSongsListResult>? = null
}