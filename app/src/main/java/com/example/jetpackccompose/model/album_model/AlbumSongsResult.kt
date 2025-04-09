package com.example.jetpackccompose.model.album_model

import androidx.media3.extractor.mp4.Track
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AlbumSongsResult {

    @SerializedName("id")
    @Expose
     val id: String? = null

    @SerializedName("name")
    @Expose
     val name: String? = null

    @SerializedName("releasedate")
    @Expose
     val releasedate: String? = null

    @SerializedName("artist_id")
    @Expose
     val artistId: String? = null

    @SerializedName("artist_name")
    @Expose
     val artistName: String? = null

    @SerializedName("image")
    @Expose
     val image: String? = null

    @SerializedName("zip")
    @Expose
     val zip: String? = null

    @SerializedName("zip_allowed")
    @Expose
     val zipAllowed: Boolean? = null

    @SerializedName("tracks")
    @Expose
     val tracks: List<AlbumSongsTrack>? = null

}
