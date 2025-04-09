package com.example.jetpackccompose.model.individual_song_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class IndividualSongsHeaders {

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

}
