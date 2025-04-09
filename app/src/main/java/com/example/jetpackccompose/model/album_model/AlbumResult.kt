package com.example.jetpackccompose.model.album_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AlbumResult {

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

    @SerializedName("shorturl")
    @Expose
     val shorturl: String? = null

    @SerializedName("shareurl")
    @Expose
     val shareurl: String? = null

    @SerializedName("zip_allowed")
    @Expose
     val zipAllowed: Boolean? = null

}
