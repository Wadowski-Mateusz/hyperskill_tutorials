package org.hyperskill.calculator

import android.os.Bundle
import android.text.InputType
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*************************************** DISPLAY ***************************************/
        val editText = findViewById<EditText>(R.id.displayEditText)
        editText.inputType = InputType.TYPE_NULL

        /*************************************** VARIABLES ***************************************/
        var operator = ""
        var olderOperator = ""
        var inputBeforeEqual = ""
        val clearListener = OnClickListener {
            editText.text.clear()
            editText.hint = "0"
            operator = ""
            olderOperator = ""
            inputBeforeEqual = ""
        }

        /*************************************** NUMBERS ***************************************/
        val listOfIdsDigitButtons = listOf(R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9)

        for(i in 0 until 9) {
            val digitButton = findViewById<Button>(listOfIdsDigitButtons[i])
            digitButton.setOnClickListener {

                if(operator == "=") // invoke clear button, if last operation was getting result
                    clearListener.onClick(null) // It just works.
                if (editText.text.toString() == "-0") // -0 -> -number (ex -4)
                    editText.setText((-i - 1).toString())
                else if (editText.text.toString() != "0")
                    editText.append((i + 1).toString())
                else    // 0 -> number (ex 4)
                    editText.setText((i + 1).toString())
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
            if(operator.isNotEmpty() && operator != "=" && editText.text.toString() != "-") {
                if (editText.text.isEmpty())
                    editText.append(editText.hint.toString())
                inputBeforeEqual = editText.text.toString()
                displayResult(editText, operator)
                olderOperator = operator
                operator = "="
            } else if (operator == "=") {
                editText.text.append(inputBeforeEqual)
                displayResult(editText, olderOperator)
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

        findViewById<Button>(R.id.clearButton).setOnClickListener(clearListener)


    } // onCreate


    private fun getDisplayValueAndClear(editText: EditText) {
        val text = editText.text.toString()
        if (text.isNotEmpty())
            editText.hint = parseStringToIntOrDouble(text)
        editText.text.clear()
    }


    private fun displayResult(editText: EditText, operator: String) {
        val result: String = calculate(editText.hint.toString(), editText.text.toString(), operator)
        editText.hint = parseStringToIntOrDouble(result)
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
            "=" -> {left + right}
            else -> {Double.NaN}
        }
        return result.toString()
    }

    private fun parseStringToIntOrDouble(number: String): String =
        if (isStringInt(number))
            number.toDouble().toInt().toString()
        else
            number.toDouble().toString()

    private fun isStringInt(number: String): Boolean =
        number.toDouble().toInt().toDouble() == number.toDouble()

}