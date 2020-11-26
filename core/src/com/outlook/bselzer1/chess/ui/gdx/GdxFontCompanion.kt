package com.outlook.bselzer1.chess.ui.gdx

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.utils.Disposable
import kotlin.math.roundToInt

/**
 * The companion for creating fonts.
 */
object GdxFontCompanion : Disposable
{
    /**
     * The default font size (from default.fnt file)
     */
    private const val DEFAULT_FONT_SIZE = 17

    /**
     * The default font location.
     */
    private const val DEFAULT_FONT_LOCATION = "default/default.fnt"

    /**
     * The main font location.
     */
    private const val FONT_LOCATION = "font/arial.ttf"

    /**
     * The free type font generator.
     */
    private val GENERATOR = FreeTypeFontGenerator(Gdx.files.internal(FONT_LOCATION))

    /**
     * The cached fonts.
     */
    private val FONTS = mutableMapOf<Int, BitmapFont>()

    /**
     * Dispose of common resources.
     */
    override fun dispose()
    {
        return GENERATOR.dispose()
    }

    /**
     * If the application is WebGL generate a BitmapFont.
     * Otherwise, generates a FreeTypeFont (not compatible with WebGL).
     *
     * @param size size of the font in pixels
     * @return a font
     */
    fun generateFont(size: Int = DEFAULT_FONT_SIZE): BitmapFont
    {
        //Cannot generate a free type font for WebGL or for a non-positive size.
        if (Gdx.app.type == Application.ApplicationType.WebGL || size <= 0)
        {
            return BitmapFont(Gdx.files.internal(DEFAULT_FONT_LOCATION))
        }

        var font = FONTS[size]
        if (font != null)
        {
            return font
        }

        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.minFilter = Texture.TextureFilter.Linear
        parameter.magFilter = Texture.TextureFilter.Linear
        parameter.size = size

        font = GENERATOR.generateFont(parameter)
        FONTS[size] = font
        return font
    }

    /**
     * Generate a font from a given size.
     */
    fun Number.generateFont(): BitmapFont
    {
        return generateFont(this.toDouble().roundToInt())
    }
}