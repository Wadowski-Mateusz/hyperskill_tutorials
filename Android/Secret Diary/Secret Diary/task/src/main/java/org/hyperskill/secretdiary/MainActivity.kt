package org.hyperskill.secretdiary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val save = findViewById<Button>(R.id.btnSave)
        val textView = findViewById<TextView>(R.id.tvDiary)
        val input = findViewById<EditText>(R.id.etNewWriting)

        save.setOnClickListener {
            if(input.text.isNotEmpty() && input.text.isNotBlank()) {
                val dateTime: Long = Clock.System.now().toEpochMilliseconds()
                val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(dateTime).toString()
                if(textView.text.isNotEmpty())
                    textView.text = "${timeStamp}\n${input.text}\n\n${textView.text}"
                else
                    textView.text = "${timeStamp}\n${input.text}"
                input.text.clear()
            } else Toast.makeText(
                applicationContext,
                "Empty or blank input cannot be saved",
                Toast.LENGTH_SHORT)
                .show()
        }


    }

}