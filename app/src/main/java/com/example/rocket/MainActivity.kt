package com.example.rocket

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout: LinearLayout
    lateinit var startbtn: Button
    lateinit var mGameview: Gameview
    lateinit var score: TextView
    lateinit var highScoreTextView: TextView
    lateinit var sharedPreferences: SharedPreferences
    override var myrocktPosition = 0 // Initialize myrocktPosition here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startbtn = findViewById(R.id.startbtn)
        rootLayout = findViewById(R.id.rootlayout)
        score = findViewById(R.id.score)
        highScoreTextView = findViewById(R.id.Highscore)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Retrieve the high score from SharedPreferences and display it
        val highScore = sharedPreferences.getInt("highScore", 0)
        highScoreTextView.text = "High Score: $highScore"

        startbtn.setOnClickListener {

        }
    }

    private var isGamePaused = false


    fun pauseGame(view: View) {
        if (isGamePaused) {

            mGameview.resumeGame()

            (view as Button).text = "Pause"
        } else {

            mGameview.pauseGame()

            (view as Button).text = "Resume"
        }
        // Toggle the game paused state
        isGamePaused = !isGamePaused
    }

    override fun closeGame(mScore: Int) {
        val highScore = sharedPreferences.getInt("highScore", 0)
        if (mScore > highScore) {
            val editor = sharedPreferences.edit()
            editor.putInt("highScore", mScore)
            editor.apply()
            highScoreTextView.text = "High Score: $mScore"
        }
        score.text = "Score : $mScore"
        rootLayout.removeView(mGameview)
        startbtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
    }

    private fun restartGame() {
        // Recreate the Gameview instance
        mGameview = Gameview(this, this)

        // Set background resource and add the Gameview to the root layout
        mGameview.setBackgroundResource(R.drawable.sky2)
        rootLayout.addView(mGameview)

        // Hide the start button and score TextView
        startbtn.visibility = View.GONE
        score.visibility = View.GONE
    }
}
