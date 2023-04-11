package watermark

import java.awt.Color
import java.io.File
import javax.imageio.ImageIO
import kotlin.NumberFormatException
import kotlin.system.exitProcess

fun main() {

    println("Input the image filename:")
    val sourceImageName: String = readln().trim()
    val sourceImage = Image(sourceImageName, "image")

    println("Input the watermark image filename:")
    val watermarkFileName: String = readln().trim()
    val watermarkImage = Image(watermarkFileName, "watermark")

    val width: Int = sourceImage.width
    val height: Int = sourceImage.height
    if (width != watermarkImage.width || height != watermarkImage.height){
        println("The image and watermark dimensions are different.")
        exitProcess(1)
    }

    val functionForColorCalculation: (Color, Color, Int) -> Color =
        if(watermarkImage.isTransparent()) {
            println("Do you want to use the watermark's Alpha channel?")
            if (readln().trim().lowercase() == "yes")
               ColorCalculator::calculateOutputColorAlpha
            else
                ColorCalculator::calculateOutputColor
        } else {
            println("Do you want to set a transparency color?.")
            if(readln().trim().lowercase() == "yes") {
                println("Input a transparency color ([Red] [Green] [Blue]):")
                val transparencyColor: Color =
                    try {
                        val transparencyColors = readln().trim().split(" ").map { it.toInt() }
                        if (transparencyColors.size != 3 || transparencyColors.any { it !in 0..255}) {
                            println("The transparency color input is invalid.")
                            exitProcess(1)
                        }
                        Color(transparencyColors[0], transparencyColors[1], transparencyColors[2])
                    } catch (e: NumberFormatException) {
                        println("The transparency color input is invalid.")
                        exitProcess(1)
                    }
                ColorCalculator.ignoreColor = transparencyColor
                ColorCalculator::calculateOutputColorIgnoreColor
            } else
                ColorCalculator::calculateOutputColor
        }


    println("Input the watermark transparency percentage (Integer 0-100):")
    val watermarkTransparencyPercentage: Int =
        try {
            val transparency = readln().trim().toInt()
            if (transparency !in 0..100) {
                println("The transparency percentage is out of range.")
                exitProcess(1)
            }
            transparency
        } catch (e: NumberFormatException) {
            println("The transparency percentage isn't an integer number.")
            exitProcess(1)
        }

    println("Input the output image filename (jpg or png extension):")
    val outputFileName: String = readln().trim()
    if (outputFileName.split(".").last() != "jpg" && outputFileName.split(".").last() != "png") {
        println("The output file extension isn't \"jpg\" or \"png\".")
        exitProcess(1)
    }

    val outputImage = Image(sourceImageName, "output")

    for (wIndex in 1..width) {
        for (hIndex in 1..height) {
            val sourceColor = Color(sourceImage.bufferedImage.getRGB(wIndex - 1, hIndex - 1))
            val watermarkColor = Color(watermarkImage.bufferedImage.getRGB(wIndex - 1, hIndex - 1),true)
            val outputColor: Color = functionForColorCalculation(sourceColor, watermarkColor, watermarkTransparencyPercentage)
            outputImage.bufferedImage.setRGB(wIndex - 1, hIndex -1, outputColor.rgb)
        }
    }

    ImageIO.write(outputImage.bufferedImage, outputFileName.split(".").last(), File(outputFileName))
    println("The watermarked image $outputFileName has been created.")
}
