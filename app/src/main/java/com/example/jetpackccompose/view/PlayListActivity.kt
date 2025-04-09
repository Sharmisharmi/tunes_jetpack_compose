package com.example.jetpackccompose.view

import PlayerManager2
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import coil.compose.AsyncImage
import com.example.jetpackccompose.utils.CommonMethods
import com.example.jetpackccompose.R
import com.example.jetpackccompose.model.playlist_mode.PlayListSongsResponse
import com.example.jetpackccompose.model.playlist_mode.PlayListSongsResult
import com.example.jetpackccompose.ui.theme.JetpackcComposeTheme
import com.example.jetpackccompose.view_model.HomeVieModel
import kotlinx.coroutines.delay

class PlayListActivity : ComponentActivity() {

    var getPlayListId = ""
    var getPlayListName = ""
    private val home_viewmodel:HomeVieModel = HomeVieModel()

    private val playListSongs = mutableStateOf<List<PlayListSongsResult>>(emptyList())

    private val isLoading = mutableStateOf(true)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PlayerManager2.init(this)


        getPlayListId = intent.getStringExtra("PlayListId").toString()
        getPlayListName = intent.getStringExtra("PlayListName").toString()

        playLists(getPlayListId)
        setContent {
            JetpackcComposeTheme(darkTheme = false) {
                Scaffold(
                    topBar = {
                        MyTopBar(title = getPlayListName) { finish() }
                    }
                ) { padding ->
                    Box(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize()
                    ) {
                        if (isLoading.value) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(50.dp)
                                    .align(Alignment.Center),
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            playListsColumn(playList = playListSongs.value)
                        }
                    }
                }
            }
        }
    }

    fun playLists(getplayListId: String) {
        isLoading.value = true

        home_viewmodel.getPlayLists(CommonMethods.client_id,"json", getplayListId,10,"mp32")
            .observe(this) { res: PlayListSongsResponse ->
                res.results?.let { playList ->
                    Log.d("PlayList", "Fetched Playlist: ${playList!![0].name.toString()}")
                    playListSongs.value = playList
                }
                isLoading.value = false

            }

    }
}

@Composable
fun playListsColumn(playList: List<PlayListSongsResult>){

    val exoPlayer = PlayerManager2.getPlayerPlayList() ?: return
    var currentSong by remember { mutableStateOf(PlayerManager2.getCurrentSongPlayList()) }
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
            items(playList) { playSong ->
                val isCurrent = currentSong?.id == playSong.id
                PlayListSongItem(
                    modifier = Modifier.padding(6.dp),
                    playListSong = playSong,
                    isPlaying = isCurrent && isPlaying,
                    songProgress = if (isCurrent) songProgress else 0L,
                    songDuration = if (isCurrent) songDuration else 1L,
                    onPlayPauseClick = {
                        if (isCurrent && isPlaying) {
                            exoPlayer.pause()
                            isPlaying = false
                        } else {
                            currentSong = playSong
                            PlayerManager2.playListSong(playSong)
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
            BottomMusicPlayerPlayLIst(
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
fun PlayListSongItem(
    modifier: Modifier,
    playListSong: PlayListSongsResult,
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
                model = playListSong.albumImage,
                contentDescription = "album_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Text(
                text = playListSong.name ?: "Unknown",
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
fun BottomMusicPlayerPlayLIst(
    currentSong: PlayListSongsResult,
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
                        .putExtra("FromIntent","PlayListActivity")
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
                    text = currentSong.name ?: "Unknown",
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
fun MyTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, color = Color.Black,
            modifier = Modifier.fillMaxWidth(),

            textAlign = TextAlign.Center
        ) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(

                    painter = painterResource(id= R.drawable.arrow_back),
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
    )
}

@Preview(showBackground = true)
@Composable
fun MyTopBarPreview() {
    JetpackcComposeTheme {
        MyTopBar(title = "PlayList", onBack = {})
    }
}
