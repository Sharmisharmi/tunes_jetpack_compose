package com.example.jetpackccompose.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import coil.compose.AsyncImage
import com.example.jetpackccompose.utils.CommonMethods
import com.example.jetpackccompose.R
import com.example.jetpackccompose.model.artist_model.FetchPopularArtistResponse
import com.example.jetpackccompose.model.artist_model.PopularArtistResult
import com.example.jetpackccompose.model.album_model.AlbumResult
import com.example.jetpackccompose.model.album_model.FetchAlbumResponse
import com.example.jetpackccompose.model.playlist_mode.FetchTopPlayListResponse
import com.example.jetpackccompose.model.playlist_mode.TopPlayListResult
import com.example.jetpackccompose.ui.theme.JetpackcComposeTheme
import com.example.jetpackccompose.view_model.HomeVieModel

class MainActivity : ComponentActivity() {

    private val homeviewModel = HomeVieModel()

    // ✅ Mutable states to hold playlist and artist data
    private val playListState = mutableStateOf<List<TopPlayListResult>>(emptyList())
    private val artistListState = mutableStateOf<List<PopularArtistResult>>(emptyList())
    private val albumListState = mutableStateOf<List<AlbumResult>>(emptyList())




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Show system bars (status + nav)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true  // dark icons on light background
        insetsController.show(WindowInsetsCompat.Type.statusBars())

        // Fetch data
        fetchPlaylist(CommonMethods.client_id)
        fetchPopularArtistList(CommonMethods.client_id)
        fetchAlbumList(CommonMethods.client_id)

        setContent {



            JetpackcComposeTheme(darkTheme = false) {   // Force white theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item { AppTitle(name = "Rythms", modifier = Modifier.padding(16.dp)) }
                        item { playListRow(playList = playListState.value) }
                        item { PopulaArtistRow(artists = artistListState.value) }
                        item { AlbumRow(album = albumListState.value) }
                    }


                }
            }
        }

    }




    private fun fetchPopularArtistList(client_id : String) {
        homeviewModel.getPopularArtistLists(client_id,"json","popularity_total",10)
            .observe(this) { res: FetchPopularArtistResponse ->
                res.results?.let { artistList ->
                    Log.d("PopulaArtistName", "Fetched Artists: ${artistList.size}")
                    artistListState.value = artistList
                }
            }
    }

    private fun fetchPlaylist(client_id : String) {
        homeviewModel.getTopPlayLists(client_id,"json",10)
            .observe(this) { res: FetchTopPlayListResponse ->
                res.results?.let { playList ->
                    Log.d("PopulaArtistName", "Fetched Artists: ${playList.size}")
                    playListState.value = playList
                }
            }
    }


    private fun fetchAlbumList(client_id : String) {
        homeviewModel.getAlbumLists(client_id,"json",10)
            .observe(this) { res: FetchAlbumResponse ->
                res.results?.let { albumList ->
                    Log.d("AlbumList", "Fetched Albums: ${albumList.size}")
                    albumListState.value = albumList
                }
            }
    }
}

@Composable
private fun playListRow(playList: List<TopPlayListResult>) {

    Column (
        modifier = Modifier
            .padding(16.dp)
            .offset(y = 15.dp)
    ){

        Text(
            text = "Top PlayList",
            color = Color.Black,
            fontSize = 17.sp,
            fontFamily = CommonMethods.popins_semiBold,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(start = 0.dp, end = 16.dp)
        ) {
            itemsIndexed(playList) { index, playList ->
                PlayListItem(
                    playList = playList,
                    modifier = Modifier
                        .padding(
                            start = if (index == 0) 0.dp else 8.dp,
                            end = 8.dp
                        )
                        .padding(top = 8.dp)
                )
            }
        }
    }

}

@Composable
fun PlayListItem(playList: TopPlayListResult, modifier: Modifier) {

    // ✅ Classic random colors with a balanced palette
    val classicColors = listOf(
        Color(0xFFB0E0E6),  // Powder Blue
        Color(0xFFCCEE8C),  // Sandy Brown
        Color(0xFFFFD700),  // Gold
        Color(0xFF98FB98),  // Pale Green
        Color(0xFFFFA07A),  // Light Salmon
        Color(0xFF87CEEB),  // Sky Blue
        Color(0xFF20B2AA),  // Light Sea Green
        Color(0xFFF5A1F2),  // Orchid
        Color(0xFFF3BEB8),  // Salmon
        Color(0xFFF7A799)   // Tomato
    )

    val randomColor = classicColors.random()
    var context = LocalContext.current
   
    Card(
        modifier = modifier
            .width(170.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                val intent = Intent(context, PlayListActivity::class.java).putExtra(
                    "PlayListId",
                    playList.id.toString()
                ) .putExtra("PlayListName",playList.name.toString())
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(6.dp),
                colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = randomColor  // ✅ Apply random color
                )
    ) {


            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

//                AsyncImage(model = playList.pictureXl, contentDescription = "Play List",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .size(50.dp)
//                        .clip(CircleShape))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp),  // Fill remaining space
                    contentAlignment = Alignment.Center   // Horizontally center the text
                ) {
                    Text(
                        text = playList.name ?: "Unknown Playlist",
                        color = Color.Black,       // Changed to black for better readability
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = CommonMethods.popins_regular,
                        maxLines = 2
                    )
                }
            }
    }
}


@Composable
fun PopulaArtistRow(artists: List<PopularArtistResult>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .offset(y = 20.dp)
    ) {

        Text(
            text = "Popular Artist",
            color = Color.Black,
            fontSize = 17.sp,
            fontFamily = CommonMethods.popins_semiBold,
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(start = 0.dp, end = 16.dp)
        ) {
            itemsIndexed(artists) { index, artist ->
                ListItem(
                    artist = artist,
                    modifier = Modifier
                        .padding(
                            start = if (index == 0) 0.dp else 8.dp,  // No padding for the first card
                            end = 8.dp
                        )
                        .padding(top = 8.dp)
                )
            }
        }
    }
}


@Composable
fun ListItem(artist: PopularArtistResult, modifier: Modifier = Modifier) {

    var context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                var intent = Intent(context, ArtistSongsListActivity::class.java).putExtra(
                    "artist_id",
                    artist.id.toString()
                ).putExtra(
                    "artist_name",
                    artist.name.toString()
                )
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            // ✅ Text Overlay on Image (placed last, on top of gradient)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
            // ✅ Load Image with Coil
            AsyncImage(
                model = artist.image,  // API image URL
                contentDescription = "Artist Image",
                contentScale = ContentScale.Crop,
                alignment = Alignment.CenterStart,
                placeholder = painterResource(id = R.drawable.banner_image),
                error = painterResource(id = R.drawable.banner_image),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )


                Text(
                    text = artist.name ?: "Unknown Artist",
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontFamily = CommonMethods.popins_medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun AlbumRow(album : List<AlbumResult>){


    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        
        Text(text = "Albums",
            color = Color.Black,
            fontSize = 17.sp,
            fontFamily = CommonMethods.popins_semiBold
        )

        LazyRow (modifier = Modifier
            .fillMaxWidth()) {

            itemsIndexed(album){
                index: Int, album: AlbumResult ->

                AlbumListItem( album = album,
                        modifier = Modifier
                            .padding(
                                start = if (index == 0) 0.dp else 8.dp,  // No padding for the first card
                                end = 8.dp
                            )
                            .padding(top = 8.dp))
            }


        }
        
    }
    
    
}

@Composable
fun AlbumListItem(album: AlbumResult, modifier: Modifier) {

    var context = LocalContext.current
    Card(
        modifier = modifier
            .size(140.dp)
            .clip(RoundedCornerShape(4.dp))
            .padding(bottom = 8.dp, top = 8.dp)
            .clickable {
                var intent = Intent(context, AlbumsListActivity::class.java).putExtra(
                    "album_id",
                    album.id.toString()
                ).putExtra(
                    "album_name",
                    album.name.toString()
                )
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background Image
            AsyncImage(
                model = album.image,
                contentDescription = "Album Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Text overlay
            Text(
                text = album.name ?: "Unknown Album",
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = CommonMethods.popins_regular,
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(colorResource(id = R.color.skyblue))
                    .align(Alignment.BottomCenter)               // position at bottom
            )
        }
    }
}


@Composable
fun AppTitle(name: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .offset(y = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            // Empty spacer to center the text horizontally
//            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.tunes_image),
                contentDescription = "Tunes Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(100.dp)
                    .width(40.dp)
                    .weight(3f)
            )

            Image(
                painter = painterResource(id = R.drawable.banner_image),
                contentDescription = "Banner Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .weight(.4f)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Card(
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.banner_image_1),
                contentDescription = "Banner Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackcComposeTheme {
        AppTitle(name = "Rythms", color = Color.Blue)
    }
}
