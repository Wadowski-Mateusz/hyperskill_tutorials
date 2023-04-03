package cryptography

import java.awt.Color
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

fun main() {
    while(true) {
        println("Task (hide, show, exit):")
        val choice: String = readln().trim()
        when (choice) {
            "hide" -> hide()
            "show" -> show()
            "exit" -> break
            else -> println("Wrong task: $choice")
        }
    }
    println("Bye!")
}

fun show() {
    println("Input image file:")
    val inputFilename: String = readln().trim()

    println("Password:")
    val password: String = readln().trim()
    val passwordBitArray = stringToBooleanArray(password)

    val bufferedImage = try {
        ImageIO.read(File(inputFilename))
    } catch (e: IOException) {
        println("Can't read input file!")
        return
    }
    val endingSequence = "0 0 3".split(" ").map { it.toByte() }
    val endingSequenceAsBits = buildString {
        endingSequence.map { append(it.toString(2).padStart(8, '0'))}
    }

    val encodedMessage = buildString {
        loop@for (h in 0 until bufferedImage.height) {
            for (w in 0 until bufferedImage.width) {
                val color = Color(bufferedImage.getRGB(w, h))
                append(if (decodeBitFromBlue(color)) '1' else '0')
                if (indexOf(endingSequenceAsBits) > -1) { //coś nie dziala
                    break@loop
                }
            }
        }
    }

    val message = encodedMessage
        .substring(0, encodedMessage.length - 24)
        .mapIndexed { id, it ->
            (it == '1') xor passwordBitArray[id % passwordBitArray.size]
        }
        .map { if (it) '1' else '0' }
        .joinToString("")
        .chunked(8)
        .map{ it.toByte(2) }
        .toByteArray()
        .toString(Charsets.UTF_8)

    println("Message: $message")
}



fun hide() {
    println("Input image file:")
    val inputFilename: String = readln().trim()

    println("Output image file:")
    val outputFilename: String = readln().trim()

    println("Message to hide:")
    val message: String = readln().trim()
    val messageAsByteArray: ByteArray = message.encodeToByteArray()
    val endingSequence = "0 0 3".split(" ").map { it.toByte() }

    println("Password:")
    val password: String = readln().trim()
    val passwordBitArray = stringToBooleanArray(password)

    val bufferedImage = try {
        ImageIO.read(File(inputFilename))
    } catch (e: IOException) {
        println("Can't read input file!")
        return
    }

    val width = bufferedImage.width
    val height = bufferedImage.height

    if (width * height < (messageAsByteArray.size + 3) * 8 ) { // +3 for code STOP; *8 byte -> bit
        println("The input image is not large enough to hold this message.")
        return
    }

    val messageAsBitArray = buildList<Boolean> {
        for (byte: Byte in messageAsByteArray) {
            for (bit: Char in byte.toString(2).padStart(8, '0'))
                add(bit == '1')
        }
    }

    val hideAsBitArray =  buildList<Boolean> {
        for (byte in endingSequence) {
            for (bit: Char in byte.toString(2).padStart(8, '0'))
                add(bit == '1')
        }
    }
    // encode message
    try {
        for (h in 0 until height) {
            for (w in 0 until width) {
                val color = Color(bufferedImage.getRGB(w, h))
                val encodedColor = encodeBitInBlueValue(
                    color,
                    messageAsBitArray[h * width + w] xor passwordBitArray[(h * width + w) % passwordBitArray.size]
                )
                bufferedImage.setRGB(w, h, encodedColor.rgb)
            }
        }
    } catch (e: IndexOutOfBoundsException) { // end of message before reaching EOF
        // pass
    }
    // encode end sequence
    try {
        val hStart = messageAsBitArray.size / width
        val wStart = messageAsBitArray.size % width
        for (h in 0 until height) {
            for (w in 0 until width) {
                val color = Color(bufferedImage.getRGB(wStart + w, hStart + h))
                val encodedColor = encodeBitInBlueValue(
                    color,
                    hideAsBitArray[h * width + w]
                )
                bufferedImage.setRGB(wStart + w, hStart + h, encodedColor.rgb)
            }
        }
    } catch (e: IndexOutOfBoundsException) { // end of end sequence before reaching EOF
        // pass
    }

    try {
        val outputFile = File(outputFilename)
        val fileExtension: String = outputFilename.split(".").last()
        ImageIO.write(bufferedImage, fileExtension, outputFile)
    } catch (e: IOException) {
        println("Can't write to output file!")
        return
    }

    println("Message saved in $outputFilename image.")
}

/**
 * Return sent Color with the least significant bit of: red, green, blue; changed to 0 or 1
 *
 * @param color
 * @param bit if true, change to 1; if false, change to 0
 * @return modified Color
 * */
fun encodeColor(color: Color, bit: Boolean): Color =
    if (bit)
        Color(color.red or 1, color.green or 1, color.blue or 1) // changes last bits to 1
    else
        Color(color.red and 254, color.green and 254, color.blue and 254) // changes last bits to 0

/**
 * Return sent Color with the least significant bit of blue changed to 0 or 1
 *
 *  @param color Color to encode bit
 *  @bit it `true` change to 1; else change to 0
 *  @return Color with encoded bit
 */
fun encodeBitInBlueValue(color: Color, bit: Boolean): Color =
    if (bit)
        Color(color.red, color.green, color.blue or 1) // changes last bits to 1
    else
        Color(color.red, color.green, color.blue and 254) // changes last bits to 0


// change the least significant bit to the most significant
// if value is negative, this bit is 1, otherwise it is 0
fun decodeBitFromBlue(color: Color): Boolean = color.blue.rotateRight(1) < 0

fun stringToBooleanArray(str: String): BooleanArray {
    val strByteArray = str.toByteArray()
    val strBitArray = strByteArray
        .map {
            it
                .toString(2)
                .padStart(8, '0')
        }
        .joinToString("")
        .map { it == '1' }
        .toBooleanArray()
    return strBitArray
}