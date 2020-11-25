package com.outlook.bselzer1.chess.sharedfunctions.gdx

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.utils.Disposable

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
     * The free type font generator.
     */
    private val GENERATOR = FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"))

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
            return BitmapFont(Gdx.files.internal("default/default.fnt"))
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
        return font
    }
}