package org.hyperskill.secretdiary

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.*
import java.text.SimpleDateFormat
import java.util.*


const val PREF_DIARY = "PREF_DIARY"
class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val textView = findViewById<TextView>(R.id.tvDiary)
        textView.movementMethod = ScrollingMovementMethod() // scrolling vertically
        val input = findViewById<EditText>(R.id.etNewWriting)

        sharedPreferences = getSharedPreferences(PREF_DIARY, Context.MODE_PRIVATE)
        loadNotes(textView)

        val save = findViewById<Button>(R.id.btnSave)
        save.setOnClickListener {
            if(input.text.isNotEmpty() && input.text.isNotBlank()) {
                val dateTime: Long = Clock.System.now().toEpochMilliseconds()
                val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(dateTime).toString()
                textView.text =
                    if(textView.text.isNotEmpty()) "${timeStamp}\n${input.text}\n\n${textView.text}"
                    else "${timeStamp}\n${input.text}"
                input.text.clear()
                saveNotes(textView)
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
            .setPositiveButton("Yes") { _, _ ->
                removeLastNote(textView)
                saveNotes(textView)
            }
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

    private fun saveNotes(textView: TextView) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("KEY_DIARY_TEXT", textView.text.toString()).apply()
    }

    private fun loadNotes(textView: TextView) {
        textView.text = sharedPreferences.getString("KEY_DIARY_TEXT", "") // like Map.getOrDefault()
    }


}