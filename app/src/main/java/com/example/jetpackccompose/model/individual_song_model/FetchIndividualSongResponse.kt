package com.example.jetpackccompose.model.individual_song_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FetchIndividualSongResponse {

    @SerializedName("headers")
    @Expose
     val headers: IndividualSongsHeaders? = null

    @SerializedName("results")
    @Expose
     val results: List<IndividualSongResult>? = null
}