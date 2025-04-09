package com.example.jetpackccompose.model.album_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FetchAlbumResponse {

    @SerializedName("headers")
    @Expose
    val headers: AlbumHeaders? = null

    @SerializedName("results")
    @Expose
    val results: List<AlbumResult>? = null
}