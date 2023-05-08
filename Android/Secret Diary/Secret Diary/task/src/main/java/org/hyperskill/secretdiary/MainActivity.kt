package org.hyperskill.secretdiary

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.tvDiary)
        val input = findViewById<EditText>(R.id.etNewWriting)

        val save = findViewById<Button>(R.id.btnSave)
        save.setOnClickListener {
            if(input.text.isNotEmpty() && input.text.isNotBlank()) {
                val dateTime: Long = Clock.System.now().toEpochMilliseconds()
                val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(dateTime).toString()
                textView.text =
                    if(textView.text.isNotEmpty()) "${timeStamp}\n${input.text}\n\n${textView.text}"
                    else "${timeStamp}\n${input.text}"
                input.text.clear()
            } else Toast.makeText(
                applicationContext,
                "Empty or blank input cannot be saved",
                Toast.LENGTH_SHORT)
                .show()
        }

        val undo = findViewById<Button>(R.id.btnUndo)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Remove last note")
            .setMessage("Do you really want to remove the last writing? This operation Cannot be undone!")
            .setPositiveButton("Yes") { _, _ -> removeLastNote(textView) }
            .setNegativeButton("No", null)

        undo.setOnClickListener {
            dialog.show()
        }

    }

    private fun removeLastNote(textView: TextView) {
        if (textView.text.isNotEmpty()) {
            val begin = textView.text.indexOf("\n\n") + 2
            textView.text =
                if (begin > 1) textView.text.substring(begin, textView.text.length)
                else ""
        }
    }

}