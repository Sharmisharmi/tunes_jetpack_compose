package com.example.jetpackccompose.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.jetpackccompose.R
import kotlinx.coroutines.delay

class FullScreenSOngActivity : ComponentActivity() {

    var getIntent =""
    var getalbumImage =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        getIntent = intent.getStringExtra("FromIntent").toString()
        getalbumImage = intent.getStringExtra("album_image").toString()

        // ✅ DO NOT reinitialize ExoPlayer here, use the existing one from PlayerManager
        setContent {
            if (getIntent.equals("PlayListActivity")) {
                FullScreenSongPlayListScreen()
            } else if (getIntent.equals("AlbumListActivity")) {
                FullScreenSongAlbumScreen(getalbumImage)
            } else {
                FullScreenSongScreen()

            }
        }
    }
}
@Composable
fun FullScreenSongAlbumScreen(getalbumImage: String) {
    val player = PlayerManager3.getPlayerPlayList()
    val song = PlayerManager3.getCurrentSongPlayList()

    var isPlaying by remember { mutableStateOf(player?.isPlaying == true) }
    var progress by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(1L) }

    // 🔁 Update track progress while playing
    LaunchedEffect(isPlaying) {
        while (isPlaying && player != null) {
            progress = player.currentPosition
            duration = player.duration.takeIf { it > 0 } ?: 1L
            delay(1000)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // 🔷 Album art as full-screen background
        getalbumImage.let {
            AsyncImage(
                model = it,
                contentDescription = "Album Art Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // 🔷 White card content on top of image
        song?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(it.name ?: "Unknown Album", fontSize = 20.sp, color = Color.Black)
//                        Text(it.artistName ?: "Unknown Artist", fontSize = 16.sp, color = Color.Gray)

                        Spacer(modifier = Modifier.height(16.dp))

                        Slider(
                            value = progress.toFloat(),
                            valueRange = 0f..duration.toFloat(),
                            onValueChange = { newValue -> player?.seekTo(newValue.toLong()) },
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF87CEEB),
                                activeTrackColor = Color(0xFF87CEEB),
                                inactiveTrackColor = Color(0x4A87CEEB)
                            )
                        )

                        // 🔢 Track time
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(formatTime(progress), fontSize = 12.sp, color = Color.Gray)
                            Text(formatTime(duration), fontSize = 12.sp, color = Color.Gray)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        IconButton(onClick = {
                            if (player?.isPlaying == true) {
                                player.pause()
                            } else {
                                player?.play()
                            }
                            isPlaying = player?.isPlaying == true
                        }) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPlaying) R.drawable.round_pause_circle_24
                                    else R.drawable.round_play_circle_filled_24
                                ),
                                contentDescription = "Play/Pause",
                                tint = Color(0xFF87CEEB),
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }
                }
            }
        } ?: run {
            // ❗ Fallback if song is null
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No song is currently playing", color = Color.Red)
            }
        }
    }
}

@Composable
fun FullScreenSongPlayListScreen() {
    val player = PlayerManager2.getPlayerPlayList()
    val song = PlayerManager2.getCurrentSongPlayList()

    var isPlaying by remember { mutableStateOf(player?.isPlaying == true) }
    var progress by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(1L) }

    // 🔁 Update track progress while playing
    LaunchedEffect(isPlaying) {
        while (isPlaying && player != null) {
            progress = player.currentPosition
            duration = player.duration.takeIf { it > 0 } ?: 1L
            delay(1000)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // 🔷 Album art as full-screen background
        song?.albumImage?.let {
            AsyncImage(
                model = it,
                contentDescription = "Album Art Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // 🔷 White card content on top of image
        song?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(it.albumName ?: "Unknown Album", fontSize = 20.sp, color = Color.Black)
                        Text(it.artistName ?: "Unknown Artist", fontSize = 16.sp, color = Color.Gray)

                        Spacer(modifier = Modifier.height(16.dp))

                        Slider(
                            value = progress.toFloat(),
                            valueRange = 0f..duration.toFloat(),
                            onValueChange = { newValue -> player?.seekTo(newValue.toLong()) },
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF87CEEB),
                                activeTrackColor = Color(0xFF87CEEB),
                                inactiveTrackColor = Color(0x4A87CEEB)
                            )
                        )

                        // 🔢 Track time
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(formatTime(progress), fontSize = 12.sp, color = Color.Gray)
                            Text(formatTime(duration), fontSize = 12.sp, color = Color.Gray)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        IconButton(onClick = {
                            if (player?.isPlaying == true) {
                                player.pause()
                            } else {
                                player?.play()
                            }
                            isPlaying = player?.isPlaying == true
                        }) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPlaying) R.drawable.round_pause_circle_24
                                    else R.drawable.round_play_circle_filled_24
                                ),
                                contentDescription = "Play/Pause",
                                tint = Color(0xFF87CEEB),
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }
                }
            }
        } ?: run {
            // ❗ Fallback if song is null
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No song is currently playing", color = Color.Red)
            }
        }
    }
}

@Composable
fun FullScreenSongScreen() {
    val player = PlayerManager.getPlayer()
    val song = PlayerManager.getCurrentSong()

    var isPlaying by remember { mutableStateOf(player?.isPlaying == true) }
    var progress by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(1L) }

    // 🔁 Update track progress while playing
    LaunchedEffect(isPlaying) {
        while (isPlaying && player != null) {
            progress = player.currentPosition
            duration = player.duration.takeIf { it > 0 } ?: 1L
            delay(1000)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // 🔷 Album art as full-screen background
        song?.albumImage?.let {
            AsyncImage(
                model = it,
                contentDescription = "Album Art Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // 🔷 White card content on top of image
        song?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(it.albumName ?: "Unknown Album", fontSize = 20.sp, color = Color.Black)
                        Text(it.artistName ?: "Unknown Artist", fontSize = 16.sp, color = Color.Gray)

                        Spacer(modifier = Modifier.height(16.dp))

                        Slider(
                            value = progress.toFloat(),
                            valueRange = 0f..duration.toFloat(),
                            onValueChange = { newValue -> player?.seekTo(newValue.toLong()) },
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF87CEEB),
                                activeTrackColor = Color(0xFF87CEEB),
                                inactiveTrackColor = Color(0x4A87CEEB)
                            )
                        )

                        // 🔢 Track time
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(formatTime(progress), fontSize = 12.sp, color = Color.Gray)
                            Text(formatTime(duration), fontSize = 12.sp, color = Color.Gray)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        IconButton(onClick = {
                            if (player?.isPlaying == true) {
                                player.pause()
                            } else {
                                player?.play()
                            }
                            isPlaying = player?.isPlaying == true
                        }) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPlaying) R.drawable.round_pause_circle_24
                                    else R.drawable.round_play_circle_filled_24
                                ),
                                contentDescription = "Play/Pause",
                                tint = Color(0xFF87CEEB),
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }
                }
            }
        } ?: run {
            // ❗ Fallback if song is null
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No song is currently playing", color = Color.Red)
            }
        }
    }
}

fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}

