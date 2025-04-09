package com.example.jetpackccompose.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import coil.compose.AsyncImage
import com.example.jetpackccompose.utils.CommonMethods
import com.example.jetpackccompose.R
import com.example.jetpackccompose.model.artist_model.ArtistSongsListResponse
import com.example.jetpackccompose.model.artist_model.ArtistSongsListResult
import com.example.jetpackccompose.ui.theme.JetpackcComposeTheme
import com.example.jetpackccompose.view_model.HomeVieModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ArtistSongsListActivity : ComponentActivity() {

    private var get_artist_id: String? = null
    private var get_artist_name: String? = null
    private val artist_songs_list = mutableStateOf<List<ArtistSongsListResult>>(emptyList())
    private val home_view_model = HomeVieModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        // ✅ Initialize ExoPlayer safely
        PlayerManager.init(this)

        get_artist_id = intent.getStringExtra("artist_id") ?: ""
        get_artist_name = intent.getStringExtra("artist_name") ?: ""

        if (get_artist_id!!.isNotEmpty()) {
            fetchArtistSongsList(CommonMethods.client_id, get_artist_id!!)
        } else {
            Log.e("ArtistSongsListActivity", "Artist ID is null or empty")
        }

        setContent {
            JetpackcComposeTheme(darkTheme = false) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBar(title = get_artist_name.toString(), onBack = { finish() }) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ArtistSongsColumn(artistSongs = artist_songs_list.value)
                    }
                }
            }
        }
    }

    private fun fetchArtistSongsList(client_id: String, artist_id: String) {
        lifecycleScope.launch {
            home_view_model.get_artist_songs_list_data(client_id, "json", artist_id, 10)
                .observe(this@ArtistSongsListActivity) { res: ArtistSongsListResponse ->
                    res.results?.let { playList ->
                        if (playList.isNotEmpty()) {
                            artist_songs_list.value = playList
                        }
                    }
                }
        }
    }
}

@Composable
fun ArtistSongsColumn(artistSongs: List<ArtistSongsListResult>) {
    val exoPlayer = PlayerManager.getPlayer() ?: return
    var currentSong by remember { mutableStateOf(PlayerManager.getCurrentSong()) }
    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
    var songProgress by remember { mutableStateOf(0L) }
    var songDuration by remember { mutableStateOf(1L) }

    LaunchedEffect(isPlaying, currentSong) {
        while (isPlaying) {
            songProgress = exoPlayer.currentPosition
            songDuration = exoPlayer.duration.takeIf { it > 0 } ?: 1L
            delay(1000)
        }
    }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) isPlaying = false
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(artistSongs) { song ->
                val isCurrent = currentSong?.id == song.id
                ArtistSongItem(
                    modifier = Modifier.padding(6.dp),
                    artistSongs = song,
                    isPlaying = isCurrent && isPlaying,
                    songProgress = if (isCurrent) songProgress else 0L,
                    songDuration = if (isCurrent) songDuration else 1L,
                    onPlayPauseClick = {
                        if (isCurrent && isPlaying) {
                            exoPlayer.pause()
                            isPlaying = false
                        } else {
                            currentSong = song
                            PlayerManager.playSong(song)
                            isPlaying = true
                        }
                    },
                    onSeek = {
                        if (isCurrent) exoPlayer.seekTo(it)
                    }
                )
            }
        }

        if (currentSong != null) {
            BottomMusicPlayer(
                currentSong = currentSong!!,
                isPlaying = isPlaying,
                songProgress = songProgress,
                songDuration = songDuration,
                onPlayPauseClick = {
                    if (isPlaying) exoPlayer.pause() else exoPlayer.play()
                    isPlaying = !isPlaying
                },
                onSeek = { exoPlayer.seekTo(it) },
                exo_player = exoPlayer
            )
        }
    }
}

@Composable
fun ArtistSongItem(
    modifier: Modifier,
    artistSongs: ArtistSongsListResult,
    isPlaying: Boolean,
    songProgress: Long,
    songDuration: Long,
    onPlayPauseClick: () -> Unit,
    onSeek: (Long) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                model = artistSongs.albumImage,
                contentDescription = "album_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Text(
                text = artistSongs.albumName ?: "Unknown",
                fontSize = 13.sp,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            )

            IconButton(onClick = onPlayPauseClick) {
                Icon(
                    painter = painterResource(
                        id = if (isPlaying) R.drawable.pause_24 else R.drawable.play_24
                    ),
                    contentDescription = "Play/Pause",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun BottomMusicPlayer(
    currentSong: ArtistSongsListResult,
    isPlaying: Boolean,
    songProgress: Long,
    songDuration: Long,
    onPlayPauseClick: () -> Unit,
    onSeek: (Long) -> Unit,
    exo_player: Player
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                context.startActivity(
                    Intent(context, FullScreenSOngActivity::class.java)
                        .putExtra("currentSong_id", currentSong.id)
                )
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = currentSong.albumImage,
                contentDescription = "album_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .padding(5.dp)
            )

            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = currentSong.albumName ?: "Unknown",
                    fontSize = 13.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Slider(
                    value = songProgress.toFloat(),
                    valueRange = 0f..songDuration.toFloat(),
                    onValueChange = { onSeek(it.toLong()) },
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF87CEEB),
                        activeTrackColor = Color(0xFF87CEEB),
                        inactiveTrackColor = Color(0x4A87CEEB)
                    ),
                    modifier = Modifier.padding(top = 5.dp)
                )
            }

            IconButton(onClick = onPlayPauseClick) {
                Icon(
                    painter = painterResource(
                        id = if (isPlaying) R.drawable.round_pause_circle_24
                        else R.drawable.round_play_circle_filled_24
                    ),
                    contentDescription = "Play/Pause",
                    tint = Color(0xFF87CEEB),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, color = Color.Black,
            modifier = Modifier.fillMaxWidth(),

            textAlign = TextAlign.Center
        ) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
    )
}
