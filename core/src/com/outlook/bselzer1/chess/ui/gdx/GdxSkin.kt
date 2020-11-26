package com.outlook.bselzer1.chess.ui.gdx

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Disposable
import com.outlook.bselzer1.chess.ui.gdx.GdxFontCompanion.generateFont
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxLabel
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxSelectBox
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTextButton

/**
 * Abstraction over a skin.
 */
class GdxSkin(private val skin: Skin) : Disposable
{
    /**
     * Get a copy of the skin's text button style.
     */
    val textButtonStyle: TextButton.TextButtonStyle
        get()
        {
            return TextButton.TextButtonStyle(rawTextButtonStyle)
        }

    /**
     * Get a copy of the skin's label style.
     */
    val labelStyle: Label.LabelStyle
        get()
        {
            return Label.LabelStyle(rawLabelStyle)
        }

    /**
     * Get a copy of the skin's select box style.
     */
    val selectBoxStyle: SelectBox.SelectBoxStyle
        get()
        {
            return SelectBox.SelectBoxStyle(rawSelectBoxStyle)
        }

    /**
     * Get a copy of the skin's window style.
     */
    val windowStyle: Window.WindowStyle
        get()
        {
            return Window.WindowStyle(rawWindowStyle)
        }

    /**
     * Get the raw text button style.
     */
    private val rawTextButtonStyle: TextButton.TextButtonStyle
        get()
        {
            return skin.get(TextButton.TextButtonStyle::class.java)
        }

    /**
     * Get the raw label style.
     */
    private val rawLabelStyle: Label.LabelStyle
        get()
        {
            return skin.get(Label.LabelStyle::class.java)
        }

    /**
     * Get the raw select box style.
     */
    private val rawSelectBoxStyle: SelectBox.SelectBoxStyle
        get()
        {
            return skin.get(SelectBox.SelectBoxStyle::class.java)
        }

    /**
     * Get the raw window style.
     */
    private val rawWindowStyle: Window.WindowStyle
        get()
        {
            return skin.get(Window.WindowStyle::class.java)
        }

    /**
     * Setup the skin with the standard fonts.
     */
    internal fun setup()
    {
        rawLabelStyle.apply {
            font = GdxLabel.STANDARD_FONT_SIZE.generateFont()
        }
        rawTextButtonStyle.apply {
            font = GdxTextButton.STANDARD_FONT_SIZE.generateFont()
        }
        rawSelectBoxStyle.apply {
            font = GdxSelectBox.STANDARD_FONT_SIZE.generateFont()
            listStyle.font = GdxSelectBox.STANDARD_FONT_SIZE.generateFont()
        }
    }

    /**
     * Dispose of resources.
     */
    override fun dispose()
    {
        skin.dispose()
    }
}