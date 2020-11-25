package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.outlook.bselzer1.chess.ui.gdx.GdxFontCompanion
import com.outlook.bselzer1.chess.ui.sharedfunctions.UiStandard

/**
 * Create a new font using an existing font.
 */
fun BitmapFont.copy(): BitmapFont
{
    //Use the default font size since the name does not contain the size.
    if (this.data.name == "default")
    {
        return GdxFontCompanion.generateFont()
    }

    //Get the font size from the name. The format is {$fontName-$fontSize}.
    val size = this.data.name.split('-').lastOrNull()?.toIntOrNull()
            ?: return GdxFontCompanion.generateFont()
    return GdxFontCompanion.generateFont(size)
}

/**
 * Generate a standard font.
 */
fun UiStandard.generateStandardFont(): BitmapFont
{
    return GdxFontCompanion.generateFont(this.getStandardFontSize())
}