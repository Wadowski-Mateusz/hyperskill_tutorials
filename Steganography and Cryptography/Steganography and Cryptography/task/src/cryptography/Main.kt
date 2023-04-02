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

fun hide() {
    println("Input image file:")
    val inputFilename: String = readln().trim()

    println("Output image file:")
    val outputFilename: String = readln().trim()

    println("Input Image: $inputFilename\n" +
            "Output Image: $outputFilename")

    val bufferedImage = try {
        ImageIO.read(File(inputFilename))
    } catch (e: IOException) {
        println("Can't read input file!")
        return
    }

    for (i in 0 until bufferedImage.width) {
        for (j in 0 until bufferedImage.height) {
            val color = Color(bufferedImage.getRGB(i, j))
            val encodedColor = encodeColor(color, true)
            bufferedImage.setRGB(i, j, encodedColor.rgb)
        }
    }

    try {
        val outputFile = File(outputFilename)
        val fileExtension: String = outputFilename.split(".").last()
        ImageIO.write(bufferedImage, fileExtension, outputFile)
    } catch (e: IOException) {
        println("Can't read input file!")
        return
    }
    println("Image $outputFilename is saved.")
}

/**
 * Return sent Color with the least significant bit of: red, green, blue; changed to 0 or 1
 *
 * @param color
 * @param bit if true, change to 1; if false, change to 0
 * @return modified Color
 * */
fun encodeColor(color: Color, bit: Boolean): Color =
    if(bit)
        Color(color.red or 1, color.green or 1, color.blue or 1) // changes last bits to 1
    else
        Color(color.red and 254, color.green or 254, color.blue or 254) // changes last bits to 0

fun show() {
    println("Obtaining message from image.")
}