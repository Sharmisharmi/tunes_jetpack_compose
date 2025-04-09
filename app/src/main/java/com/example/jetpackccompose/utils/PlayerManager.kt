import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.jetpackccompose.model.artist_model.ArtistSongsListResult
import com.example.jetpackccompose.model.album_model.AlbumSongsTrack
import com.example.jetpackccompose.model.playlist_mode.PlayListSongsResult

object PlayerManager {
    private var player: ExoPlayer? = null
    private var currentSong: ArtistSongsListResult? = null

    fun init(context: Context) {
        if (player == null) {
            player = ExoPlayer.Builder(context).build()
        }
    }

    fun playSong(song: ArtistSongsListResult) {
        val exoPlayer = player ?: return
        if (currentSong?.audio != song.audio) {
            exoPlayer.setMediaItem(MediaItem.fromUri(song.audio ?: return))
            exoPlayer.prepare()
            exoPlayer.play()
            currentSong = song
        } else if (!exoPlayer.isPlaying) {
            exoPlayer.play()
        }
    }

    fun getPlayer(): ExoPlayer? = player
    fun getCurrentSong(): ArtistSongsListResult? = currentSong

}

object PlayerManager2{

    private var player1: ExoPlayer? = null
    private var playListcurrentSong: PlayListSongsResult? = null

    fun init(context: Context) {
        if (player1 == null) {
            player1 = ExoPlayer.Builder(context).build()
        }
    }

    fun playListSong(playListSong: PlayListSongsResult) {
        val exoPlayer1 = player1 ?: return
        if (playListcurrentSong?.audio != playListSong.audio) {
            exoPlayer1.setMediaItem(MediaItem.fromUri(playListSong.audio ?: return))
            exoPlayer1.prepare()
            exoPlayer1.play()
            playListcurrentSong = playListSong
        } else if (!exoPlayer1.isPlaying) {
            exoPlayer1.play()
        }
    }

    fun getPlayerPlayList(): ExoPlayer? = player1
    fun getCurrentSongPlayList(): PlayListSongsResult? = playListcurrentSong
}

object PlayerManager3{

    private var player3: ExoPlayer? = null
    private var playListcurrentSong3: AlbumSongsTrack? = null

    fun init(context: Context) {
        if (player3 == null) {
            player3 = ExoPlayer.Builder(context).build()
        }
    }

    fun playListSong3(playListSong: AlbumSongsTrack) {
        val exoPlayer3 = player3 ?: return
        if (playListcurrentSong3?.audio != playListSong.audio) {
            exoPlayer3.setMediaItem(MediaItem.fromUri(playListSong.audio ?: return))
            exoPlayer3.prepare()
            exoPlayer3.play()
            playListcurrentSong3 = playListSong
        } else if (!exoPlayer3.isPlaying) {
            exoPlayer3.play()
        }
    }

    fun getPlayerPlayList(): ExoPlayer? = player3
    fun getCurrentSongPlayList(): AlbumSongsTrack? = playListcurrentSong3
}
