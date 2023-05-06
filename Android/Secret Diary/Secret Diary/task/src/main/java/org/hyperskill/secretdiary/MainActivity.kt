package org.hyperskill.secretdiary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val save = findViewById<Button>(R.id.btnSave)
        val textView = findViewById<TextView>(R.id.tvDiary)
        val input = findViewById<EditText>(R.id.etNewWriting)

        save.setOnClickListener {
            if(input.text.isNotEmpty() && input.text.isNotBlank()) {
                textView.text = input.text
                input.text.clear()
            } else Toast.makeText(
                applicationContext,
                "Empty or blank input cannot be saved",
                Toast.LENGTH_SHORT)
                .show()


        }


    }

}