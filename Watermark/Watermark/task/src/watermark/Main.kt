package watermark

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


fun main() {
    println("Input the image filename:")
    val imageName: String = readln()
    val imageFile: File = File(imageName)

    if(!imageFile.isFile) {
        println("The file $imageName doesn't exist.")
        return
    }

    val image: BufferedImage = ImageIO.read(imageFile)


    val width: Int = image.width
    val height: Int = image.height
    val numberOfComponents: Int = image.colorModel.numComponents
    val numberOfColorComponents: Int = image.colorModel.numColorComponents
    val bitsPerPixel: Int = image.colorModel.pixelSize
    val transparency: Int = image.transparency


    println("Image file: $imageName")
    println("Width: $width")
    println("Height: $height")
    println("Number of components: $numberOfComponents")
    println("Number of color components: $numberOfColorComponents")
    println("Bits per pixel: $bitsPerPixel")
//    print("Transparency: ")
    when(transparency) {
        1 -> println("Transparency: OPAQUE")
        2 -> println("Transparency: BITMASK")
        3 -> println("Transparency: TRANSLUCENT")
    }
}
