package com.example.jetpackccompose.model.playlist_mode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class TopPlayListResult {
    @SerializedName("id")
    @Expose
     val id: String? = null

    @SerializedName("name")
    @Expose
     val name: String? = null

    @SerializedName("creationdate")
    @Expose
     val creationdate: String? = null

    @SerializedName("user_id")
    @Expose
     val userId: String? = null

    @SerializedName("user_name")
    @Expose
     val userName: String? = null

    @SerializedName("zip")
    @Expose
     val zip: String? = null

    @SerializedName("shorturl")
    @Expose
     val shorturl: String? = null

    @SerializedName("shareurl")
    @Expose
     val shareurl: String? = null

}
