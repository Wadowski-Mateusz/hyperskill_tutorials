package watermark

import java.awt.image.BufferedImage
import java.io.File
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