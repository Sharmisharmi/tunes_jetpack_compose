package com.example.jetpackccompose.api


import com.example.jetpackccompose.model.artist_model.ArtistSongsListResponse
import com.example.jetpackccompose.model.artist_model.FetchPopularArtistResponse
import com.example.jetpackccompose.model.album_model.AlbumSongsResponse
import com.example.jetpackccompose.model.album_model.FetchAlbumResponse
import com.example.jetpackccompose.model.playlist_mode.FetchTopPlayListResponse
import com.example.jetpackccompose.model.playlist_mode.PlayListSongsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    // GET request to fetch lyrics by track ID
//    @Headers(
//        "X-RapidAPI-Key: ec0a152af3msh866a7373d49c5d3p14a80ejsn2b9bdbce2fe5",
//        "X-RapidAPI-Host: spotify23.p.rapidapi.com"
//    )
//    @GET("chart/0/artists")
//    fun getTrackLyricsData(): Call<GetPopulaArtistResponse>
//
//    @GET("chart/0/playlists")
//    fun getPlayListData(): Call<GetPlaylistResponse>
//
//    @GET("/playlist/{id}")
//    fun getPlayLists(@Path("id") id:Int): Call<GetPlayListSongsResponse>


    @GET("artists/")
    fun getPopularArtistList(@Query("client_id") id:String, @Query("format") format:String, @Query("order") order:String, @Query("limit") limit : Int): Call<FetchPopularArtistResponse>

    @GET("tracks/")
    fun getArtistSongsList(@Query("client_id") id:String, @Query("format") format:String, @Query("artist_id") order:String, @Query("limit") limit : Int): Call<ArtistSongsListResponse>


    @GET("playlists/")
    fun getTopPlayList(@Query("client_id") id:String, @Query("format") format:String,  @Query("limit") limit : Int): Call<FetchTopPlayListResponse>



  @GET("albums/")
    fun getAlbumList(@Query("client_id") id:String, @Query("format") format:String,  @Query("limit") limit : Int): Call<FetchAlbumResponse>


  @GET("albums/tracks/")
    fun getAlbumSongList(@Query("client_id") id:String, @Query("id") album_id:String): Call<AlbumSongsResponse>


  @GET("tracks/")
    fun getPlayListSong(@Query("client_id") client_id:String, @Query("format") format:String, @Query("playlist_id") playlist_id:String, @Query("limit") limit : Int, @Query("audioformat") audioformat:String): Call<PlayListSongsResponse>


}