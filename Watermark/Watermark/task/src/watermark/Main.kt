package watermark

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.lang.NumberFormatException
import javax.imageio.ImageIO
import kotlin.system.exitProcess


class Image(val fileName: String, val name: String) {
    val imageFile: File = File(fileName)
    val bufferedImage: BufferedImage
    val width: Int
    val height: Int
    val numberOfComponents: Int
    val numberOfColorComponents: Int
    val bitsPerPixel: Int
    val transparency: Int

    init {
        if(!imageFile.isFile) {
            println("The file $fileName doesn't exist.")
            exitProcess(1)
        }
        bufferedImage= ImageIO.read(imageFile)
        width = bufferedImage.width
        height = bufferedImage.height
        numberOfComponents = bufferedImage.colorModel.numComponents
        numberOfColorComponents = bufferedImage.colorModel.numColorComponents
        bitsPerPixel = bufferedImage.colorModel.pixelSize
        transparency = bufferedImage.transparency
        validateImage()
    }

    fun getTransparencyName(): String = when(this.transparency) {
            1 -> "OPAQUE"
            2 -> "BITMASK"
            3 -> "TRANSLUCENT"
            else -> {"ILLEGAL TRANSPARENCY ID"}
    }

    fun isTransparent(): Boolean = transparency == 3

    private fun validateImage() {
        if (numberOfColorComponents != 3) {
            println("The number of $name color components isn't 3.")
            exitProcess(1)
        }
        if (bitsPerPixel != 24 && bitsPerPixel != 32) {
            println("The $name isn't 24 or 32-bit.")
            exitProcess(1)
        }
    }
}


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
               :: calculateOutputColorTransparent
            else
                ::calculateOutputColor
        } else
            ::calculateOutputColor


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

fun calculateOutputColor(sourceColor: Color, watermarkColor: Color, weight: Int): Color =
    Color(
        (weight * watermarkColor.red + (100 - weight) * sourceColor.red) / 100,
        (weight * watermarkColor.green + (100 - weight) * sourceColor.green) / 100,
        (weight * watermarkColor.blue + (100 - weight) * sourceColor.blue) / 100
    )

fun calculateOutputColorTransparent(sourceColor: Color, watermarkColor: Color, weight: Int): Color =
    if (watermarkColor.alpha == 0)
        Color(sourceColor.red, sourceColor.green, sourceColor.blue)
    else { // for alpha = 255
        Color(
            (weight * watermarkColor.red + (100 - weight) * sourceColor.red) / 100,
            (weight * watermarkColor.green + (100 - weight) * sourceColor.green) / 100,
            (weight * watermarkColor.blue + (100 - weight) * sourceColor.blue) / 100
        )
    }
