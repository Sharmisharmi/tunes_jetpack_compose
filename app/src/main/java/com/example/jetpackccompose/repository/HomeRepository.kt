package com.example.jetpackccompose.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.jetpackccompose.api.ApiClient
import com.example.jetpackccompose.api.ApiInterface
import com.example.jetpackccompose.model.artist_model.ArtistSongsListResponse
import com.example.jetpackccompose.model.artist_model.FetchPopularArtistResponse
import com.example.jetpackccompose.model.album_model.AlbumSongsResponse
import com.example.jetpackccompose.model.album_model.FetchAlbumResponse
import com.example.jetpackccompose.model.playlist_mode.FetchTopPlayListResponse
import com.example.jetpackccompose.model.playlist_mode.PlayListSongsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    fun requestPopularArtistLists(id:String, format : String, order:String,limit:Int): MutableLiveData<FetchPopularArtistResponse> {
        val mutableLiveData: MutableLiveData<FetchPopularArtistResponse> = MutableLiveData()


        val apiService: ApiInterface? = ApiClient.getRetrofitClient()?.create(ApiInterface::class.java)
        apiService?.getPopularArtistList(id,format,order, limit)?.enqueue(object :
            Callback<FetchPopularArtistResponse?> {
            override fun onResponse(call: Call<FetchPopularArtistResponse?>, response: Response<FetchPopularArtistResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    mutableLiveData.setValue(response.body()!! )

                    Log.d("response_raw", "onResponse: "+response.raw())



                }else{

                    Log.d("response_raw", "onError: "+response.raw())

                }
            }
            public override fun onFailure(call: Call<FetchPopularArtistResponse?>, t: Throwable?) {

                Log.d("response_raw", "onFailure: "+t.toString())



            }
        })

        return mutableLiveData
    }



    fun requestArtistSongsLists(client_id:String, format : String, artist_id:String,limit:Int): MutableLiveData<ArtistSongsListResponse> {
        val mutableLiveData: MutableLiveData<ArtistSongsListResponse> = MutableLiveData()


        val apiService: ApiInterface? = ApiClient.getRetrofitClient()?.create(ApiInterface::class.java)
        apiService?.getArtistSongsList(client_id,format,artist_id, limit)?.enqueue(object :
            Callback<ArtistSongsListResponse?> {
            override fun onResponse(call: Call<ArtistSongsListResponse?>, response: Response<ArtistSongsListResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    mutableLiveData.setValue(response.body()!! )

                    Log.d("response_raw", "onResponse: "+response.raw())



                }else{

                    Log.d("response_raw", "onError: "+response.raw())

                }
            }
            public override fun onFailure(call: Call<ArtistSongsListResponse?>, t: Throwable?) {

                Log.d("response_raw", "onFailure: "+t.toString())



            }
        })

        return mutableLiveData
    }


    fun requestTopPlayListLists(client_id:String, format : String, limit:Int): MutableLiveData<FetchTopPlayListResponse> {
        val mutableLiveData: MutableLiveData<FetchTopPlayListResponse> = MutableLiveData()


        val apiService: ApiInterface? = ApiClient.getRetrofitClient()?.create(ApiInterface::class.java)
        apiService?.getTopPlayList(client_id,format,limit)?.enqueue(object :
            Callback<FetchTopPlayListResponse?> {
            override fun onResponse(call: Call<FetchTopPlayListResponse?>, response: Response<FetchTopPlayListResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    mutableLiveData.setValue(response.body()!! )

                    Log.d("response_raw", "onResponse: "+response.raw())



                }else{

                    Log.d("response_raw", "onError: "+response.raw())

                }
            }
            public override fun onFailure(call: Call<FetchTopPlayListResponse?>, t: Throwable?) {

                Log.d("response_raw", "onFailure: "+t.toString())



            }
        })

        return mutableLiveData
    }



    fun requestAlbumLists(client_id:String, format : String, limit:Int): MutableLiveData<FetchAlbumResponse> {
        val mutableLiveData: MutableLiveData<FetchAlbumResponse> = MutableLiveData()


        val apiService: ApiInterface? = ApiClient.getRetrofitClient()?.create(ApiInterface::class.java)
        apiService?.getAlbumList(client_id,format,limit)?.enqueue(object :
            Callback<FetchAlbumResponse?> {
            override fun onResponse(call: Call<FetchAlbumResponse?>, response: Response<FetchAlbumResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    mutableLiveData.setValue(response.body()!! )

                    Log.d("response_raw", "onResponse: "+response.raw())



                }else{

                    Log.d("response_raw", "onError: "+response.raw())

                }
            }
            public override fun onFailure(call: Call<FetchAlbumResponse?>, t: Throwable?) {

                Log.d("response_raw", "onFailure: "+t.toString())



            }
        })

        return mutableLiveData
    }


    fun requestAlbumSongLists(client_id:String,album_id:String): MutableLiveData<AlbumSongsResponse> {
        val mutableLiveData: MutableLiveData<AlbumSongsResponse> = MutableLiveData()


        val apiService: ApiInterface? = ApiClient.getRetrofitClient()?.create(ApiInterface::class.java)
        apiService?.getAlbumSongList(client_id,album_id)?.enqueue(object :
            Callback<AlbumSongsResponse?> {
            override fun onResponse(call: Call<AlbumSongsResponse?>, response: Response<AlbumSongsResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    mutableLiveData.setValue(response.body()!! )

                    Log.d("response_raw", "onResponse: "+response.raw())



                }else{

                    Log.d("response_raw", "onError: "+response.raw())

                }
            }
            public override fun onFailure(call: Call<AlbumSongsResponse?>, t: Throwable?) {

                Log.d("response_raw", "onFailure: "+t.toString())



            }
        })

        return mutableLiveData
    }


    fun requestPlayListSongs(client_id:String,format : String,playlist_id:String, limit: Int, audioformat:String): MutableLiveData<PlayListSongsResponse> {
        val mutableLiveData: MutableLiveData<PlayListSongsResponse> = MutableLiveData()


        val apiService: ApiInterface? = ApiClient.getRetrofitClient()?.create(ApiInterface::class.java)
        apiService?.getPlayListSong(client_id,format,playlist_id,limit,audioformat)?.enqueue(object :
            Callback<PlayListSongsResponse?> {
            override fun onResponse(call: Call<PlayListSongsResponse?>, response: Response<PlayListSongsResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    mutableLiveData.setValue(response.body()!! )

                    Log.d("response_raw", "onResponse: "+response.raw())



                }else{

                    Log.d("response_raw", "onError: "+response.raw())

                }
            }
            public override fun onFailure(call: Call<PlayListSongsResponse?>, t: Throwable?) {

                Log.d("response_raw", "onFailure: "+t.toString())



            }
        })

        return mutableLiveData
    }




}