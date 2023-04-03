package cryptography

import java.awt.Color
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

fun main() {

    hide()

//    while(true) {
//        println("Task (hide, show, exit):")
//        val choice: String = readln().trim()
//        when (choice) {
//            "hide" -> hide()
//            "show" -> show()
//            "exit" -> break
//            else -> println("Wrong task: $choice")
//        }
//    }
//    println("Bye!")
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

    val bufferedImage = try {
        ImageIO.read(File(inputFilename))
    } catch (e: IOException) {
        println("Can't read input file!")
        return
    }

    if (bufferedImage.width * bufferedImage.height < (messageAsByteArray.size + 3) * 8 ) { // +3 for code STOP; *8 byte -> bit
        println("The input image is not large enough to hold this message.")
        return
    }

    val messageAsBitArray = buildList<Boolean> {
        // add message
        for (byte: Byte in messageAsByteArray) {
            for (bit: Char in byte.toString(2).padStart(8, '0'))
                add(bit == '1')
        }
        // add ending sequence
        for (byte in endingSequence) {
            for (bit: Char in byte.toString(2).padStart(8, '0'))
                add(bit == '1')
        }
    }

    // TODO switch i with j in indexes?
    try {
        for (i in 0 until bufferedImage.width) {
            for (j in 0 until bufferedImage.height) {
                val color = Color(bufferedImage.getRGB(i, j))
                val encodedColor = encodeBitInBlueValue(color, messageAsBitArray[i * bufferedImage.height + j])
                bufferedImage.setRGB(i, j, encodedColor.rgb)
            }
        }
    } catch (e: IndexOutOfBoundsException) { // end of message before reaching EOF
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

fun show() {
    println("Obtaining message from image.")
    // toString(Charsets.UTF_8) - restore message from bytes to UTF-8
}