package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap

/**
 * Fill the pixmap, ignoring any transparent pixels.
 */
fun Pixmap.replacementFill()
{
    for (x in 0 until this.width)
    {
        for (y in 0 until this.height)
        {
            val pixel = this.getPixel(x, y)

            //Ignore transparent
            if (Color.alpha(pixel.toFloat()) == 0)
            {
                continue
            }

            this.fillRectangle(x, y, 1, 1)
        }
    }
}