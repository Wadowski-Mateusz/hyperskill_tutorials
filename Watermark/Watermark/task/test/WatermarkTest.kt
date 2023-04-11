import org.hyperskill.hstest.dynamic.DynamicTest
import org.hyperskill.hstest.stage.StageTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testing.TestedProgram
import java.awt.Color
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.abs

class CardGameTest : StageTest<Any>() {

    @DynamicTest(order = 1)
    fun imageNotExistTest3(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        outputString = main.execute("test${File.separator}notexist.png").trim().lowercase()
        position = checkOutput(outputString, 0, "The file test${File.separator}notexist.png doesn't exist.".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Incorrect output, when a non existing filename was input.")

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 2)
    fun imageColorCompLess3Test3(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile = "test${File.separator}grey.png"
            val inputFile = File(infile)
            if (!inputFile.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test${File.separator}grey.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The number of image color components isn't 3.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when an image that doesn't have 3 color components was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 3)
    fun imageBitsPerPixelTest3(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile = "test${File.separator}bits16.png"
            val inputFile = File(infile)
            if (!inputFile.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test${File.separator}bits16.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The image isn't 24 or 32-bit.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when an image with no 24 or 32 bits per pixel was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 4)
    fun watermarkNotExistTest3(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile = "test${File.separator}image3.png"
            val inputFile = File(infile)
            if (!inputFile.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test${File.separator}image3.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test${File.separator}notexist.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The file test${File.separator}notexist.png doesn't exist.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when a non existing watermark filename was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 5)
    fun watermarkColorCompLess3Test3(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test${File.separator}image3.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test${File.separator}grey.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test${File.separator}image3.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test${File.separator}grey.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The number of watermark color components isn't 3.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when a watermark that doesn't have 3 color components was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 6)
    fun watermarkBitsPerPixelTest3(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test${File.separator}image3.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test${File.separator}bits16.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test${File.separator}image3.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test${File.separator}bits16.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The watermark isn't 24 or 32-bit.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when a watermark with no 24 or 32 bits per pixel was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 7)
    fun notSameDimensionsTest3(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test${File.separator}image3.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test${File.separator}70x50.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test${File.separator}image3.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test${File.separator}70x50.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The image and watermark dimensions are different.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when The image and watermark dimensions are different."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 8)
    fun noAlphaChannelTest3(): CheckResult {
        try {
            val outFile = File("test${File.separator}out1.png")
            if (outFile.exists()) outFile.delete()
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to delete a previous created output file.")
        }

        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test${File.separator}image3.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test${File.separator}watermark2.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test${File.separator}image3.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test${File.separator}watermark2.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark transparency percentage (Integer 0-100):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after a watermark filename was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        outputString = main.execute("30").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the output image filename (jpg or png extension):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the transparency percentage was input."
        )

        outputString = main.execute("test${File.separator}out1.png").trim().lowercase()
        position = checkOutput(outputString, 0, "The watermarked image test${File.separator}out1.png has been created.".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the output filename was input."
        )

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        val outFile = File("test${File.separator}out1.png")
        if (!outFile.exists()) return CheckResult(
            false,
            "The output file hasn't been created."
        )

        if (!checkIfCorrectOutputFileS3(30, 50000,
                "test${File.separator}image3.png", "test${File.separator}watermark2.png", "test${File.separator}out1.png")) return CheckResult(
            false,
            "Incorrect output image file."
        )

        return CheckResult.correct()
    }

    @DynamicTest(order = 9)
    fun notUsingAlphaChannelTest3(): CheckResult {
        try {
            val outFile = File("test${File.separator}out2.png")
            if (outFile.exists()) outFile.delete()
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to delete a previous created output file.")
        }

        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test${File.separator}image3.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test${File.separator}watermark3.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test${File.separator}image3.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test${File.separator}watermark3.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Do you want to use the watermark's Alpha channel?".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after a watermark filename was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        outputString = main.execute("no").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the watermark transparency percentage (Integer 0-100):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after declining to use the alpha channel."
        )

        outputString = main.execute("40").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the output image filename (jpg or png extension):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the transparency percentage was input."
        )

        outputString = main.execute("test${File.separator}out2.png").trim().lowercase()
        position = checkOutput(outputString, 0, "The watermarked image test${File.separator}out2.png has been created.".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the output filename was input."
        )

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        val outFile = File("test${File.separator}out2.png")
        if (!outFile.exists()) return CheckResult(
            false,
            "The output file hasn't been created."
        )

        if (!checkIfCorrectOutputFileS3(40, 50000,
                "test${File.separator}image3.png", "test${File.separator}watermark3.png", "test${File.separator}out2.png")) return CheckResult(
            false,
            "Incorrect output image file."
        )

        return CheckResult.correct()
    }

    @DynamicTest(order = 10)
    fun usingAlphaChannelTest3(): CheckResult {
        try {
            val outFile = File("test${File.separator}out3.png")
            if (outFile.exists()) outFile.delete()
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to delete a previous created output file.")
        }

        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test${File.separator}image3.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test${File.separator}watermark3.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test${File.separator}image3.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test${File.separator}watermark3.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Do you want to use the watermark's Alpha channel?".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after a watermark filename was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        outputString = main.execute("yes").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the watermark transparency percentage (Integer 0-100):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after declining to use the alpha channel."
        )

        outputString = main.execute("20").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the output image filename (jpg or png extension):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the transparency percentage was input."
        )

        outputString = main.execute("test${File.separator}out3.png").trim().lowercase()
        position = checkOutput(outputString, 0, "The watermarked image test${File.separator}out3.png has been created.".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the output filename was input."
        )

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        val outFile = File("test${File.separator}out3.png")
        if (!outFile.exists()) return CheckResult(
            false,
            "The output file hasn't been created."
        )

        if (!checkIfCorrectOutputFileAlphaS3(20, 50000,
                "test${File.separator}image3.png", "test${File.separator}watermark3.png", "test${File.separator}out3.png")) return CheckResult(
            false,
            "Incorrect output image file."
        )

        return CheckResult.correct()
    }

}

fun checkOutput(outputString: String, searchPos: Int, vararg checkStr: String): Int {
    var searchPosition = searchPos
    for (str in checkStr) {
        val findPosition = outputString.indexOf(str, searchPosition)
        if (findPosition == -1) return -1
        if ( outputString.substring(searchPosition until findPosition).isNotBlank() ) return -1
        searchPosition = findPosition + str.length
    }
    return searchPosition
}

fun checkIfCorrectOutputFileS3(per: Int, err: Long,
                               imageStr: String, watStr: String, outStr: String): Boolean {
    val imageFile = File(imageStr)
    val image = ImageIO.read(imageFile)
    val watermarkFile = File(watStr)
    val watermark = ImageIO.read(watermarkFile)
    val outFile = File(outStr)
    val outputImage = ImageIO.read(outFile)
    var diff: Long = 0
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val i = Color(image.getRGB(x, y))
            val w = Color(watermark.getRGB(x, y))
            val o = Color(outputImage.getRGB(x ,y))
            val oc = Color(
                ((100 - per) * i.red + per * w.red) / 100,
                ((100 - per) * i.green + per * w.green) / 100,
                ((100 - per) * i.blue + per * w.blue) / 100
            )
            diff += abs(oc.red - o.red) + abs(oc.green - o.green) + abs(oc.blue - o.blue)
        }
    }
    return diff <= err
}

fun checkIfCorrectOutputFileAlphaS3(per: Int, err: Long,
                                    imageStr: String, watStr: String, outStr: String): Boolean {
    val imageFile = File(imageStr)
    val image = ImageIO.read(imageFile)
    val watermarkFile = File(watStr)
    val watermark = ImageIO.read(watermarkFile)
    val outFile = File(outStr)
    val outputImage = ImageIO.read(outFile)
    var diff: Long = 0
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val i = Color(image.getRGB(x, y))
            val w = Color(watermark.getRGB(x, y), true)
            val o = Color(outputImage.getRGB(x ,y))
            val oc = if (w.alpha == 0) Color(i.red, i.green, i.blue)
            else Color(
                ((100 - per) * i.red + per * w.red) / 100,
                ((100 - per) * i.green + per * w.green) / 100,
                ((100 - per) * i.blue + per * w.blue) / 100
            )
            diff += abs(oc.red - o.red) + abs(oc.green - o.green) + abs(oc.blue - o.blue)
        }
    }
    return diff <= err
}


