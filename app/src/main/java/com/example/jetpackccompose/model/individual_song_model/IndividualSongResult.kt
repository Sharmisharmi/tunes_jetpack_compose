package com.example.jetpackccompose.model.individual_song_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class IndividualSongResult {

    @SerializedName("id")
    @Expose
     val id: String? = null

    @SerializedName("name")
    @Expose
     val name: String? = null

    @SerializedName("duration")
    @Expose
     val duration: Int? = null

    @SerializedName("artist_id")
    @Expose
     val artistId: String? = null

    @SerializedName("artist_name")
    @Expose
     val artistName: String? = null

    @SerializedName("artist_idstr")
    @Expose
     val artistIdstr: String? = null

    @SerializedName("album_name")
    @Expose
     val albumName: String? = null

    @SerializedName("album_id")
    @Expose
     val albumId: String? = null

    @SerializedName("license_ccurl")
    @Expose
     val licenseCcurl: String? = null

    @SerializedName("position")
    @Expose
     val position: Int? = null

    @SerializedName("releasedate")
    @Expose
     val releasedate: String? = null

    @SerializedName("album_image")
    @Expose
     val albumImage: String? = null

    @SerializedName("audio")
    @Expose
     val audio: String? = null

    @SerializedName("audiodownload")
    @Expose
     val audiodownload: String? = null

    @SerializedName("prourl")
    @Expose
     val prourl: String? = null

    @SerializedName("shorturl")
    @Expose
     val shorturl: String? = null

    @SerializedName("shareurl")
    @Expose
     val shareurl: String? = null

    @SerializedName("waveform")
    @Expose
     val waveform: String? = null

    @SerializedName("image")
    @Expose
     val image: String? = null

    @SerializedName("audiodownload_allowed")
    @Expose
     val audiodownloadAllowed: Boolean? = null

}
