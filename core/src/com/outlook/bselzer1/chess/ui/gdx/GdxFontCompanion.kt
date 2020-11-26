package com.outlook.bselzer1.chess.ui.gdx

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.GdxRuntimeException
import com.outlook.bselzer3.libgdxlogger.LibgdxLogger

/**
 * The companion for creating fonts.
 */
object GdxFontCompanion : Disposable
{
    /**
     * The default font location.
     */
    private const val DEFAULT_FONT_LOCATION = "default/default.fnt"

    /**
     * The main font location.
     */
    private const val FONT_LOCATION = "font/arial.ttf"

    /**
     * The default font.
     */
    private val DEFAULT_FONT = BitmapFont(Gdx.files.internal(DEFAULT_FONT_LOCATION))

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
    fun generateFont(size: Int): BitmapFont
    {
        //Cannot generate a free type font for WebGL or for a non-positive size.
        if (Gdx.app.type == Application.ApplicationType.WebGL)
        {
            return DEFAULT_FONT
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

        try
        {
            font = GENERATOR.generateFont(parameter)
        }
        catch (e: GdxRuntimeException)
        {
            LibgdxLogger.error("Unable to create a font with size $size: $e")
            return DEFAULT_FONT
        }

        FONTS[size] = font
        return font
    }

    /**
     * Generate a font from a given size.
     */
    fun Number.generateFont(): BitmapFont
    {
        return generateFont(this.toInt())
    }
}