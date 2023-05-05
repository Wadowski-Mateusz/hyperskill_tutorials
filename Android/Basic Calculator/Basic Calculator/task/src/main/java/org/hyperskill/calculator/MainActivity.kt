package org.hyperskill.calculator

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*************************************** VARIABLES ***************************************/
        var operator = ""

        /*************************************** DISPLAY ***************************************/
        val editText = findViewById<EditText>(R.id.displayEditText)
        editText.inputType = InputType.TYPE_NULL

        /*************************************** NUMBERS ***************************************/
        val listOfIdsDigitButtons = listOf(R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9)

        for(i in 0 until 9) {
            val digitButton = findViewById<Button>(listOfIdsDigitButtons[i])
            digitButton.setOnClickListener {

                if (editText.text.toString() == "-0") // -0 -> -number (ex -4)
                    editText.setText((-i-1).toString())
                else if(editText.text.toString() != "0")
                    editText.append((i+1).toString())
                else    // 0 -> number (ex 4)
                    editText.setText((i+1).toString())
            }
        }

        findViewById<Button>(R.id.button0).setOnClickListener  {
            if(editText.text.toString() != "0")
                editText.append("0")
        }

        /*************************************** OPERATORS ***************************************/

        // DIVISION /
        findViewById<Button>(R.id.divideButton).setOnClickListener  {
            if(operator.isEmpty() || operator == "=") {
                getDisplayValueAndClear(editText)
            }
            operator = "/"
        }

        // MULTIPLICATION *
        findViewById<Button>(R.id.multiplyButton).setOnClickListener  {
            if(operator.isEmpty() || operator == "=") {
                getDisplayValueAndClear(editText)
            }
            operator = "*"
        }

        // SUBTRACTION -
        findViewById<Button>(R.id.subtractButton).setOnClickListener  {

            if (editText.text.isNotEmpty() || operator == "=") {
                if (operator.isEmpty()) {
                    getDisplayValueAndClear(editText)
                }
                operator = "-"
            } else if (editText.text.isEmpty()){
                editText.text.append("-")
            }
            else {
                operator = "-"
            }
        }

        // ADDITION +
        findViewById<Button>(R.id.addButton).setOnClickListener  {
            if(operator.isEmpty() || operator == "=") {
                getDisplayValueAndClear(editText)
            }
            operator = "+"
        }

        // EQUAL =
        findViewById<Button>(R.id.equalButton).setOnClickListener  {
            if(operator.isNotEmpty() && operator != "=" && editText.text.isNotEmpty() && editText.text.toString() != "-") {
                displayResult(editText, operator)
                operator = "="
            }
        }

        /*************************************** OTHERS ***************************************/

        // Decimal separator
        findViewById<Button>(R.id.dotButton).setOnClickListener {
            if (!editText.text.toString().contains(".")) {
                if (editText.text.toString().isEmpty() || editText.text.toString() == "-")
                    editText.append("0.")
                else
                    editText.append(".")
            }
        }

        findViewById<Button>(R.id.clearButton).setOnClickListener {
            editText.text.clear()
            editText.hint = "0"
            operator = ""
        }

    }



    private fun getDisplayValueAndClear(editText: EditText): String {
        if (editText.text.toString().isNotEmpty())
            editText.hint = editText.text.toString().toDouble().toString()
        val result =  editText.text.toString()
        editText.text.clear()
        return result
    }

    private fun displayResult(editText: EditText, operator: String) {
        val result = calculate(editText.hint.toString(), editText.text.toString(), operator)
        editText.hint = result
        editText.text.clear()
    }

    private fun calculate(leftNumber: String, rightNumber: String, operator: String): String {
        val left = leftNumber.toDouble()
        val right = rightNumber.toDouble()
        val result = when (operator) {
            "/" -> {left / right}
            "*" -> {left * right}
            "-" -> {left - right}
            "+" -> {left + right}
            "=" -> {Double.NaN}
            else -> {Double.NaN}
        }
        return result.toString()
    }


}