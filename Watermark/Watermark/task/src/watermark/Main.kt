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

    if (sourceImage.width < watermarkImage.width || sourceImage.height < watermarkImage.height){
        println("The watermark's dimensions are larger.")
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
    val transparencyPercentage: Int =
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

    println("Choose the position method (single, grid):")
    val typeOfWatermark = readln().trim()
    if (typeOfWatermark != "single" && typeOfWatermark != "grid") {
        println("The position method input is invalid.")
        exitProcess(1)
    }

     val (xStart: Int, yStart: Int) =
         if (typeOfWatermark == "single") {
             val diffX: Int = sourceImage.width - watermarkImage.width
             val diffY: Int = sourceImage.height - watermarkImage.height
             println("Input the watermark position ([x 0-$diffX] [y 0-$diffY]):")
             val position: List<Int> = try {
                 val position = readln().split(" ").map { it.toInt() }
                 if (position.size != 2) {
                     println("Wrong number of dimensions.") // own
                     exitProcess(1)
                 }
                 position
             } catch (e: java.lang.NumberFormatException) {
                 println("The position input is invalid.")
                 exitProcess(1)
             }
             if (position[0] !in 0..diffX || position[1] !in 0..diffY) {
                 println("The position input is out of range.")
                 exitProcess(1)
             }
             position
         } else {
             listOf(0,0)
         }

    println("Input the output image filename (jpg or png extension):")
    val outputFileName: String = readln().trim()
    if (outputFileName.split(".").last() != "jpg" && outputFileName.split(".").last() != "png") {
        println("The output file extension isn't \"jpg\" or \"png\".")
        exitProcess(1)
    }

    val outputImage: Image =
        if (typeOfWatermark == "grid")
            gridWatermark(sourceImage, watermarkImage, transparencyPercentage, functionForColorCalculation)
        else  // typeOfWatermark == "single"
            singleWatermark(sourceImage, watermarkImage, xStart, yStart, transparencyPercentage, functionForColorCalculation)


    ImageIO.write(outputImage.bufferedImage, outputFileName.split(".").last(), File(outputFileName))
    println("The watermarked image $outputFileName has been created.")
}

fun singleWatermark(sourceImage: Image, watermarkImage: Image, xStart: Int, yStart: Int,
                    transparencyPercentage: Int, calculationFunction: (Color, Color, Int) -> Color): Image {

    val outputImage = Image(sourceImage.fileName, "output")
    var xWatermark = 0
    var yWatermark = 0

    for (xIndex in xStart until xStart + watermarkImage.width) {
        for (yIndex in yStart until yStart + watermarkImage.height) {
            val sourceColor = Color(sourceImage.bufferedImage
                .getRGB(xIndex, yIndex))
            val watermarkColor = Color(watermarkImage.bufferedImage
                .getRGB(xWatermark, yWatermark),true)
            val outputColor: Color = calculationFunction(sourceColor, watermarkColor, transparencyPercentage)
            outputImage.bufferedImage.setRGB(xIndex, yIndex, outputColor.rgb)
            yWatermark++
        }
        yWatermark = 0
        xWatermark++
    }

    return outputImage
}

fun gridWatermark(sourceImage: Image, watermarkImage: Image,
                  transparencyPercentage: Int, calculationFunction: (Color, Color, Int) -> Color): Image {

    val outputImage = Image(sourceImage.fileName, "output")

    var xWatermark = 0
    var yWatermark = 0
    for (columnIndex in 0 until sourceImage.width) {
        for (rowIndex in 0 until sourceImage.height) {
            val sourceColor = Color(sourceImage.bufferedImage.getRGB(columnIndex, rowIndex))
            val watermarkColor = Color(
                watermarkImage.bufferedImage
                    .getRGB(xWatermark, yWatermark), true)
            val outputColor: Color = calculationFunction(sourceColor, watermarkColor, transparencyPercentage)
            outputImage.bufferedImage.setRGB(columnIndex, rowIndex, outputColor.rgb)
            yWatermark = (1 + yWatermark) % watermarkImage.height
        }
        yWatermark = 0
        xWatermark = (1 + xWatermark) % watermarkImage.width
    }

    return outputImage
}
