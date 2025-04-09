package com.example.jetpackccompose.model.album_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AlbumSongsTrack {


    @SerializedName("id")
    @Expose
     val id: String? = null

    @SerializedName("position")
    @Expose
     val position: String? = null

    @SerializedName("name")
    @Expose
     val name: String? = null

    @SerializedName("duration")
    @Expose
     val duration: String? = null

    @SerializedName("license_ccurl")
    @Expose
     val licenseCcurl: String? = null

    @SerializedName("audio")
    @Expose
     val audio: String? = null

    @SerializedName("audiodownload")
    @Expose
     val audiodownload: String? = null

    @SerializedName("audiodownload_allowed")
    @Expose
     val audiodownloadAllowed: Boolean? = null
}
