package com.example.jetpackccompose.model.playlist_mode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PlayListSongsResponse {

    @SerializedName("headers")
    @Expose
     val headers: PlayListSongsHeaders? = null

    @SerializedName("results")
    @Expose
     val results: List<PlayListSongsResult>? = null
}