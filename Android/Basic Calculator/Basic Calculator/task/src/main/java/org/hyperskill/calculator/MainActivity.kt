package org.hyperskill.calculator

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // VARIABLES
        var hasDot = false

        // DISPLAY
        val editText = findViewById<EditText>(R.id.displayEditText)
        editText.inputType = InputType.TYPE_NULL

        // NUMBERS
        val listOfIdsDigitButtons = listOf(R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9)

        for(i in 0 until 9) {
            val digitButton = findViewById<Button>(listOfIdsDigitButtons[i])
            digitButton.setOnClickListener {
                if(editText.text.toString() != "0")
                    editText.append((i+1).toString())
                else
                    editText.setText((i+1).toString())
            }
        }

        findViewById<Button>(R.id.button0).setOnClickListener  {
            if(editText.text.toString() != "0")
                editText.append("0")
        }

        // OPERATORS

        // OTHERS
        findViewById<Button>(R.id.dotButton).setOnClickListener {
            if (!hasDot) {
                hasDot = true
                if (editText.text.toString().isEmpty())
                    editText.append("0.")
                else
                    editText.append(".")
            }
        }

        findViewById<Button>(R.id.clearButton).setOnClickListener {
            editText.text.clear()
            hasDot = false
        }
    }

}