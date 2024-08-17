package com.example.alarmclock.common

import android.content.Context
import android.media.MediaPlayer


class AlarmSound(context: Context?, soundResource: Int) {

    private val mediaPlayer: MediaPlayer = MediaPlayer.create(context, soundResource)

    fun startSound() {
        mediaPlayer.isLooping = true
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun stopSound() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.prepare()
        }
    }

    fun release() {
        mediaPlayer.release()
    }
}