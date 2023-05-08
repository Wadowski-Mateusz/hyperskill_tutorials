package org.hyperskill.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.textView)

        var isTimerStarted = false

        val start = findViewById<Button>(R.id.startButton)


        start.setOnClickListener {
            if(!isTimerStarted) {
                isTimerStarted = true
                handler.postDelayed(updateTimer, 1000)
//                handler.postAtTime(updateTimer, System.currentTimeMillis() + 1000) // doesn't work with this
            }
        }



        val reset = findViewById<Button>(R.id.resetButton)
        reset.setOnClickListener {
            if(isTimerStarted) {
                isTimerStarted = false
                handler.removeCallbacks(updateTimer)
                tv.text = "00:00"
            }
        }

    } // main

    private val updateTimer: Runnable = object : Runnable {
        override fun run() {
            handler.post{ addSecond() }
//            findViewById<TextView>(R.id.textView).text = (System.currentTimeMillis() + 1000).toString()
//            handler.postAtTime(this, System.currentTimeMillis() + 1000)
            handler.postDelayed(this, 1000)
        }
    }

    fun addSecond() {
        val tv = findViewById<TextView>(R.id.textView)
        var (min, sec) = tv.text.split(":").map{ it.toInt() }
        if (++sec == 60) {
            sec = 0
            min += 1
        }
        tv.text = String.format("%02d:%02d", min, sec)
    }

}