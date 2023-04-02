package images

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import java.awt.Color

const val height: Int = 800
const val width: Int = 600

fun saveImage(image: BufferedImage, imageFile: File) {
    ImageIO.write(image, "png", imageFile)
}

fun drawTriangle(): BufferedImage {
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.color = Color.GREEN
    graphics.drawPolygon(intArrayOf(10, 20, 30), intArrayOf(100, 20, 100), 3)
    return image
}

fun drawPolygon(): BufferedImage {
    val image = BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.color = Color.GREEN
    graphics.drawPolygon(intArrayOf(50, 100, 200, 250, 200, 100), intArrayOf(150, 250, 250, 150, 50, 50), 6)
    return image
}

fun drawSquare(): BufferedImage {
    val image = BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.color = Color.RED
    graphics.drawPolygon(intArrayOf(100, 400, 400, 100), intArrayOf(100, 100, 400, 400), 4)
    return image
}

fun main() {
    val imageFile = File("src/main/kotlin/images/square.png")
    //    val image: BufferedImage = ImageIO.read(imageFile)
    saveImage(drawSquare(), imageFile)
}