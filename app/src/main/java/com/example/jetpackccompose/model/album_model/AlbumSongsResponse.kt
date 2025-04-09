package com.example.jetpackccompose.model.album_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AlbumSongsResponse {

    @SerializedName("headers")
    @Expose
     val headers: AlbumSongsHeaders? = null

    @SerializedName("results")
    @Expose
     val results: List<AlbumSongsResult>? = null
    
}