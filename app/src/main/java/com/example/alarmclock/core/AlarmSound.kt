package com.example.alarmclock.core

import android.content.Context
import android.media.MediaPlayer
import com.example.alarmclock.R


class AlarmSound(context: Context, soundResource: Int) {

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