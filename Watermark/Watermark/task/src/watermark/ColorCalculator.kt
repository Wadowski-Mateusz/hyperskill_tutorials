package watermark

import java.awt.Color

object ColorCalculator{

    var ignoreColor: Color = Color(255, 255, 255)

    fun calculateOutputColor(sourceColor: Color, watermarkColor: Color, weight: Int): Color =
        Color(
            (weight * watermarkColor.red + (100 - weight) * sourceColor.red) / 100,
            (weight * watermarkColor.green + (100 - weight) * sourceColor.green) / 100,
            (weight * watermarkColor.blue + (100 - weight) * sourceColor.blue) / 100
        )

    fun calculateOutputColorAlpha(sourceColor: Color, watermarkColor: Color, weight: Int): Color =
        if (watermarkColor.alpha == 0)
            Color(sourceColor.red, sourceColor.green, sourceColor.blue)
        else { // for alpha = 255
            Color(
                (weight * watermarkColor.red + (100 - weight) * sourceColor.red) / 100,
                (weight * watermarkColor.green + (100 - weight) * sourceColor.green) / 100,
                (weight * watermarkColor.blue + (100 - weight) * sourceColor.blue) / 100
            )
        }

    fun calculateOutputColorIgnoreColor(sourceColor: Color, watermarkColor: Color, weight: Int): Color =
        if (watermarkColor == this.ignoreColor)
            Color(sourceColor.red, sourceColor.green, sourceColor.blue)
        else {
            Color(
                (weight * watermarkColor.red + (100 - weight) * sourceColor.red) / 100,
                (weight * watermarkColor.green + (100 - weight) * sourceColor.green) / 100,
                (weight * watermarkColor.blue + (100 - weight) * sourceColor.blue) / 100
            )
        }

}