package com.joerae.project4

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {

    // media player
    var mediaPlayer: MediaPlayer? = null
    // play music - set to true for the start
    private var playMusic: Boolean = true
    // get current position when music pauses. seems easier to use a global variable here than saving it in preferences
    private var mediaPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        //let's play a song if playMusic is true
        if (playMusic && mediaPlayer == null) {
            // don't ask me why I chose Stanky and the Coal Miners for the music, but I did...
            mediaPlayer = MediaPlayer.create(this, R.raw.hatsofftopennsylvania)
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.start()
        }

        // get images
        val images: IntArray = intArrayOf(
            // note the incredibly descriptive image names...
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six
        )

        // View Pager
        val viewPager: ViewPager = findViewById<View>(R.id.pager) as ViewPager

        // adapter
        val adapter: PagerAdapter = ImagePagerAdaptor(this, images)

        // bind adapter to viewpager
        viewPager.adapter = adapter

        // https://www.raywenderlich.com/324-viewpager-tutorial-getting-started-in-kotlin
        // this will allow for "negative" scrolling by placing the currentItem view in the middle of the array
        viewPager.currentItem = adapter.count/2
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle the action bar here
        return when (item.itemId) {
            R.id.action_settings -> {
                // pause the player
                if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.pause()
                    mediaPosition = mediaPlayer!!.currentPosition
                }
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*  originally I had my mediaPlayer paused in here, but I moved it to onOptionsItemSelected
    override fun onPause() {
        super.onPause()

        // pause the player
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
            mediaPosition = mediaPlayer!!.currentPosition
        }
    } */

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("Play Music",
                Context.MODE_PRIVATE)
        playMusic = prefs.getBoolean("music", playMusic)

        if (playMusic) {
            // not sure if i'm coding this correctly. whenever I "pause" the music by going to the settings screen,
            // the mediaPlayer goes to a null state and the song starts from the beginning. so i worked around that
            // with the mediaPosition global variable. not sure why it's going to null when onPause is called. so it's not
            // exactly coded correctly, but it does work.  my best guess is android is running some kind of garbage collection
            // to free up resources when the intent is run?
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.hatsofftopennsylvania)
                mediaPlayer!!.isLooping = true
                mediaPlayer!!.seekTo(mediaPosition)
                mediaPlayer!!.start()
            } else {
                mediaPlayer!!.start()
            }
        }
    }

    override fun onStop() {
        super.onStop()

        // release the mediaPlayer on stop
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

}