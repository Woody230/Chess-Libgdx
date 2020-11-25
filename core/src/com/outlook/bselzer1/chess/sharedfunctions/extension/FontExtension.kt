package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.outlook.bselzer1.chess.ui.sharedfunctions.UiStandard

/**
 * The default font size (from default.fnt file)
 */
const val DEFAULT_FONT_SIZE = 17

//TODO cache same size fonts
/**
 * If the application is WebGL generate a BitmapFont.
 * Otherwise, generates a FreeTypeFont (not compatible with WebGL).
 *
 * @param size size of the font in pixels
 * @return a font
 */
fun generateFont(size: Int): BitmapFont
{
    val font: BitmapFont

    //Cannot generate a free type font for WebGL or for a non-positive size.
    if (Gdx.app.type == Application.ApplicationType.WebGL || size <= 0)
    {
        font = BitmapFont(Gdx.files.internal("default/default.fnt"))
        return font
    }

    val generator = FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"))
    val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
    parameter.minFilter = Texture.TextureFilter.Linear
    parameter.magFilter = Texture.TextureFilter.Linear
    parameter.size = size

    font = generator.generateFont(parameter)
    generator.dispose()
    return font
}

/**
 * Create a new font using an existing font.
 */
fun BitmapFont.copy(): BitmapFont
{
    //Use the default font size since the name does not contain the size.
    if (this.data.name == "default")
    {
        return generateFont(DEFAULT_FONT_SIZE)
    }

    //Get the font size from the name. The format is {$fontName-$fontSize}.
    val size = this.data.name.split('-').lastOrNull()?.toIntOrNull()
            ?: return generateFont(DEFAULT_FONT_SIZE)
    return generateFont(size)
}

/**
 * Generate a standard font.
 */
fun UiStandard.generateStandardFont(): BitmapFont
{
    return generateFont(this.getStandardFontSize())
}