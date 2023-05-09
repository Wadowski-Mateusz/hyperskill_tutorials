package org.hyperskill.stopwatch

import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlin.random.Random

const val CHANNEL_ID = "org.hyperskill"

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private var upperLimit = Int.MAX_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // NOTIFICATIONS

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Who knows"
            val descriptionText = "Sure not me"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
                .apply { description = descriptionText }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }



        // Event listeners

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tv = findViewById<TextView>(R.id.textView)
        var isTimerStarted = false

        val settingsButton = findViewById<Button>(R.id.settingsButton)
        settingsButton.setOnClickListener {
            val contentView = LayoutInflater.from(this).inflate(R.layout.settings_alert, null, false)
            AlertDialog.Builder(this)
                .setTitle("Enter Upper Time")
                .setView(contentView)
                .setPositiveButton(R.string.settings_ok) { _, _ ->
                    val et = contentView.findViewById<EditText>(R.id.upperLimitEditText)
                    upperLimit = et.text.toString().toInt()
                    et.text.clear()
                }
                .setNegativeButton(R.string.settings_cancel) { _, _ -> }
                .show()
        }

        val start = findViewById<Button>(R.id.startButton)
        start.setOnClickListener {
            if(!isTimerStarted) {
                isTimerStarted = true
                settingsButton.isEnabled = false
                progressBar.visibility = View.VISIBLE
                handler.postDelayed(updateTimer, 1000)
            }
        }

        val reset = findViewById<Button>(R.id.resetButton)
        reset.setOnClickListener {
            if(isTimerStarted) {
                handler.removeCallbacks(updateTimer)
                isTimerStarted = false
                settingsButton.isEnabled = true
                progressBar.visibility = View.GONE
                tv.text = "00:00"
                tv.setTextColor(ContextCompat.getColor(this, R.color.darkWhite))
            }
        }



    } // main


    private val updateTimer: Runnable = object : Runnable {
        override fun run() {
            handler.post {
                val isTimeUp = addSecond()
                changeProgressBarColor()
                if (isTimeUp) timeUp()
            }
            handler.postDelayed(this, 1000)
        }
    }

    private fun timeUp() {
        val tv = findViewById<TextView>(R.id.textView)
        tv.setTextColor(Color.RED)

        if(upperLimit > 0) {
            val pendingIntent = PendingIntent.getActivity(
                this,
                1,
                Intent(applicationContext, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Time's up!")
                .setContentText("beeep BEEEEEEEEEEEEEEEP")
                .setStyle(NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .build()

                val flags = notification.flags
                notification.flags = flags or Notification.FLAG_INSISTENT

            val notificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(393939, notification)
        }
    }

    private fun addSecond(): Boolean {
        val tv = findViewById<TextView>(R.id.textView)
        var (min, sec) = tv.text.split(":").map { it.toInt() }
        if (++sec == 60) {
            sec = 0
            min += 1
        }
        tv.text = String.format("%02d:%02d", min, sec)

        return 60 * min + sec >= upperLimit
    }

    private fun changeProgressBarColor() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            progressBar.indeterminateTintList = ColorStateList.valueOf(Random.nextInt())
    }

}