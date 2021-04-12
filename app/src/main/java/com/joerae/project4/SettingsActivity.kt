package com.joerae.project4

import android.content.Context
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences

class SettingsActivity : AppCompatActivity()  {

    private var playMusic: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // set up a preference
        val prefs = getSharedPreferences("Play Music", Context.MODE_PRIVATE)

        playMusic = prefs.getBoolean("music", true)

        // widget
        val musicSwitch = findViewById<Switch>(R.id.musicSwitch)

        // set switch
        musicSwitch.isChecked = playMusic

        // find if switch changed
        musicSwitch.setOnCheckedChangeListener {
            buttonView, isChecked ->
            playMusic = isChecked
        }
    }

    override fun onPause() {
        super.onPause()

        // Save the settings here
        val prefs = getSharedPreferences("Play Music", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("music", playMusic)
        editor.apply()
    }
}