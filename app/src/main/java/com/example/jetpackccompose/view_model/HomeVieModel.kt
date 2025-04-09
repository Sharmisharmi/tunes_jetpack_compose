package com.example.jetpackccompose.view_model

import androidx.lifecycle.MutableLiveData
import com.example.jetpackccompose.model.artist_model.ArtistSongsListResponse
import com.example.jetpackccompose.model.artist_model.FetchPopularArtistResponse
import com.example.jetpackccompose.model.album_model.AlbumSongsResponse
import com.example.jetpackccompose.model.album_model.FetchAlbumResponse
import com.example.jetpackccompose.model.playlist_mode.FetchTopPlayListResponse
import com.example.jetpackccompose.model.playlist_mode.PlayListSongsResponse
import com.example.jetpackccompose.repository.HomeRepository

class HomeVieModel {

    var homeRepository = HomeRepository()

    private var get_popular_artist_lists: MutableLiveData<FetchPopularArtistResponse> = MutableLiveData()

    fun getPopularArtistLists(client_id:String, format : String, order : String, limit : Int): MutableLiveData<FetchPopularArtistResponse> {
        get_popular_artist_lists = homeRepository!!.requestPopularArtistLists(client_id,format,order,limit)
        return get_popular_artist_lists
    }


    private var get_artist_songs_list: MutableLiveData<ArtistSongsListResponse> = MutableLiveData()

    fun get_artist_songs_list_data(client_id:String, format : String, artist_id : String, limit : Int): MutableLiveData<ArtistSongsListResponse> {
        get_artist_songs_list = homeRepository!!.requestArtistSongsLists(client_id,format,artist_id,limit)
        return get_artist_songs_list
    }



    private var get_top_play_list: MutableLiveData<FetchTopPlayListResponse> = MutableLiveData()

    fun getTopPlayLists(client_id:String, format : String, limit : Int): MutableLiveData<FetchTopPlayListResponse> {
        get_top_play_list = homeRepository!!.requestTopPlayListLists(client_id,format,limit)
        return get_top_play_list
    }




    private var get_album_list: MutableLiveData<FetchAlbumResponse> = MutableLiveData()

    fun getAlbumLists(client_id:String, format : String, limit : Int): MutableLiveData<FetchAlbumResponse> {
        get_album_list = homeRepository!!.requestAlbumLists(client_id,format,limit)
        return get_album_list
    }




    private var get_album_song_list: MutableLiveData<AlbumSongsResponse> = MutableLiveData()

    fun getAlbumSongsLists(client_id:String,album_id:String): MutableLiveData<AlbumSongsResponse> {
        get_album_song_list = homeRepository!!.requestAlbumSongLists(client_id,album_id)
        return get_album_song_list
    }


    private var play_lists: MutableLiveData<PlayListSongsResponse> = MutableLiveData()

    fun getPlayLists(client_id:String,format: String, playlist_id:String,limit :Int, audioformat:String): MutableLiveData<PlayListSongsResponse> {
        play_lists = homeRepository!!.requestPlayListSongs(client_id,format,playlist_id,limit,audioformat)
        return play_lists
    }
}