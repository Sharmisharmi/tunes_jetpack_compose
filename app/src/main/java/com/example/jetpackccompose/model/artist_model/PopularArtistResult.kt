package com.example.jetpackccompose.model.artist_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PopularArtistResult {

    @SerializedName("id")
    @Expose
     val id: String? = null

    @SerializedName("name")
    @Expose
     val name: String? = null

    @SerializedName("website")
    @Expose
     val website: String? = null

    @SerializedName("joindate")
    @Expose
     val joindate: String? = null

    @SerializedName("image")
    @Expose
     val image: String? = null

    @SerializedName("shorturl")
    @Expose
     val shorturl: String? = null

    @SerializedName("shareurl")
    @Expose
     val shareurl: String? = null

}
