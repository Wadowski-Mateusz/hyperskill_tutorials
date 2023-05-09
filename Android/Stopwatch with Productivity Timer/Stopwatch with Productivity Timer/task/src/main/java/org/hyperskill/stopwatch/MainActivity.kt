package org.hyperskill.stopwatch

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val tv = findViewById<TextView>(R.id.textView)
        var isTimerStarted = false

        val start = findViewById<Button>(R.id.startButton)
        start.setOnClickListener {
            if(!isTimerStarted) {
                isTimerStarted = true
                progressBar.visibility = View.VISIBLE
                handler.postDelayed(updateTimer, 1000)
            }
        }

        val reset = findViewById<Button>(R.id.resetButton)
        reset.setOnClickListener {
            if(isTimerStarted) {
                isTimerStarted = false
                progressBar.visibility = View.GONE
                handler.removeCallbacks(updateTimer)
                tv.text = "00:00"
            }
        }

    } // main

    private val updateTimer: Runnable = object : Runnable {
        override fun run() {
            handler.post { addSecond() }
            handler.post { changeProgressBarColor() }
            handler.postDelayed(this, 1000)
        }
    }

    fun addSecond() {
        val tv = findViewById<TextView>(R.id.textView)
        var (min, sec) = tv.text.split(":").map { it.toInt() }
        if (++sec == 60) {
            sec = 0
            min += 1
        }
        tv.text = String.format("%02d:%02d", min, sec)
    }

    fun changeProgressBarColor() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            progressBar.indeterminateTintList = ColorStateList.valueOf(Random.nextInt())
    }

}