package org.hyperskill.calculator.tip

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bill = 0.0
        var tip = 0
        val slider = findViewById<Slider>(R.id.slider)
        val textView = findViewById<TextView>(R.id.text_view)
        val editText = findViewById<EditText>(R.id.edit_text)

        textView.text = ""

        editText.doOnTextChanged { text, _, _, _ ->
            bill =
                if (text.isNullOrEmpty()) 0.0
                else text.toString().toDouble()

            update(textView, bill, tip)
        }

        slider.addOnChangeListener { _, value, _ ->
            tip = value.toInt()
            update(textView, bill, tip)
        }

    }

    private fun update(textView: TextView, bill: Double, tip: Int) {
        if(bill != 0.0)
            textView.text = "Tip amount: ${String.format("%.2f", (bill*tip/100.0))}$"
        else
            textView.text = ""


    }

}