package com.example.jetpackccompose.model.playlist_mode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PlayListSongsHeaders {

    @SerializedName("status")
    @Expose
     val status: String? = null

    @SerializedName("code")
    @Expose
     val code: Int? = null

    @SerializedName("error_message")
    @Expose
     val errorMessage: String? = null

    @SerializedName("warnings")
    @Expose
     val warnings: String? = null

    @SerializedName("results_count")
    @Expose
     val resultsCount: Int? = null

    @SerializedName("next")
    @Expose
     val next: String? = null

}
