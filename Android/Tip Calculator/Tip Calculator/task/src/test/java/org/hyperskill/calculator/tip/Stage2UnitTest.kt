package org.hyperskill.calculator.tip

import org.hyperskill.calculator.tip.internals.TipCalculatorUnitTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner

// Version 2.0
@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Stage2UnitTest : TipCalculatorUnitTest<MainActivity>(MainActivity::class.java) {

    companion object {
        const val expectedTextTemplate = "Bill value: %.2f$, tip percentage: %d%%"
    }

    @Test
    fun test00_checkTipWithEmptyValue() {
        testActivity {
            slider
            textView

            slider.value = 15f
            advanceClockAndRun()

            val messageTextNotEmptyError =
                "View with id \"$idTextView\" should be empty when \"$idEditText\" is empty"
            val isTextEmpty = textView.text.isNullOrEmpty()
            assertTrue(messageTextNotEmptyError, isTextEmpty)
        }
    }

    @Test
    fun test01_checkValueWithInitialTip() {
        testActivity {
            editText
            textView

            val valueToTest = 50
            editText.setText(valueToTest.toString())
            advanceClockAndRun()
            val tipToTest = 0

            val messageTextError = "View with id \"$idTextView\" should contain formatted output"
            val expectedText = expectedTextTemplate.format(valueToTest.toDouble(), tipToTest)
            val actualText = textView.text.toString()
            assertEquals(messageTextError, expectedText, actualText)
        }
    }

    @Test
    fun test02_checkBothValueAndTipWithSliderListenerLast() {
        testActivity {
            editText
            textView
            slider

            val valueToTest = 201
            editText.setText(valueToTest.toString())
            advanceClockAndRun()
            val tipToTest = 10
            slider.value = tipToTest.toFloat()
            advanceClockAndRun()

            val messageTextError = "View with id \"$idTextView\" should contain formatted output"
            val expectedText = expectedTextTemplate.format(valueToTest.toDouble(), tipToTest)
            val actualText = textView.text
            assertEquals(messageTextError, expectedText, actualText)
        }
    }

    @Test
    fun test03_checkEditTextListenerLast() {
        testActivity {
            editText
            slider
            textView

            val tipToTest = 90
            slider.value = tipToTest.toFloat()
            advanceClockAndRun()
            val valueToTest = 39
            editText.setText(valueToTest.toString())
            advanceClockAndRun()

            val messageTextError = "View with id \"$idTextView\" should contain formatted output"
            val expectedText = expectedTextTemplate.format(valueToTest.toDouble(), tipToTest)
            val actualText = textView.text.toString()
            assertEquals(messageTextError, expectedText, actualText)
        }
    }

    @Test
    fun test04_checkLargeValue() {
        testActivity {
            editText
            textView
            val messageLargeNumberTextError = "Make sure you give support for large numbers"

            try {
                val valueToTest = "100000000000000000"
                editText.setText(valueToTest)
                advanceClockAndRun()
                val tipToTest = 90
                slider.value = tipToTest.toFloat()
                advanceClockAndRun()


                val expectedText = expectedTextTemplate.format(valueToTest.toDouble(), tipToTest)
                val actualText = textView.text.toString()
                assertEquals(messageLargeNumberTextError, expectedText, actualText)
            } catch (ex: Exception) {
                throw AssertionError("Exception thrown ${ex.javaClass.simpleName}. $messageLargeNumberTextError", ex)
            }
        }
    }

    @Test
    fun test05_checkDecimalValue() {
        testActivity {
            editText
            textView

            val valueToTest = "70.12"
            editText.setText(valueToTest)
            advanceClockAndRun()
            val tipToTest = 0

            val messageDecimalNumberTextError =
                "Make sure you give support for numbers with decimal part"
            val expectedText = expectedTextTemplate.format(valueToTest.toDouble(), tipToTest)
            val actualText = textView.text.toString()
            assertEquals(messageDecimalNumberTextError, expectedText, actualText)
        }
    }

    @Test
    fun test06_checkClearingValue() {
        testActivity {
            editText
            textView

            val valueNotEmpty = "100.10"
            editText.setText(valueNotEmpty)
            advanceClockAndRun()
            val tipToTest = 10
            slider.value = tipToTest.toFloat()
            advanceClockAndRun()
            val valueEmpty = ""
            editText.setText(valueEmpty)
            advanceClockAndRun()

            val messageDecimalNumberTextError =
                "View with id \"$idTextView\" should clear if \"$idEditText\" is empty"
            val expectedText = ""
            val actualText = textView.text.toString()
            assertEquals(messageDecimalNumberTextError, expectedText, actualText)
        }
    }
}