package es.chipeit.android.ui.emulator

import android.content.Context
import android.media.MediaPlayer

class MusicPlayer(private val context: Context, private val resId: Int, private var listener: MediaPlayer.OnCompletionListener? = null) : MediaPlayer.OnCompletionListener {
    private var mediaPlayer: MediaPlayer? = null

    val isPlaying: Boolean
        get() = if (mediaPlayer != null)
            (mediaPlayer as MediaPlayer).isPlaying
        else
            false

    var isLooping: Boolean = false
        set(loopMode: Boolean) {
            field = loopMode
            mediaPlayer?.isLooping = field
        }

    fun play() {
        if (mediaPlayer != null) {
            mediaPlayer!!.start()
            return
        }

        mediaPlayer = MediaPlayer.create(context, resId)
        val mp = mediaPlayer!!

        mp.setOnCompletionListener(this)
        mp.isLooping = isLooping
        mp.start()
    }

    fun pause() {
        if (mediaPlayer == null)
            return

        mediaPlayer!!.pause()
    }

    fun stop() {
        if (mediaPlayer == null)
            return

        val mp = mediaPlayer!!

        mp.stop()
        mp.release()

        mediaPlayer = null
    }

    fun onDestroy() {
        listener = null
    }

    override fun onCompletion(mp: MediaPlayer?) {
        stop()
        listener?.onCompletion(mp)
    }
}
