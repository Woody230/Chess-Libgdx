package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.outlook.bselzer1.chess.ui.GdxGame

/**
 * Get a copy of the label style.
 */
fun Skin.getLabelStyle(): Label.LabelStyle
{
    val style = this.get(Label.LabelStyle::class.java)
    return Label.LabelStyle(style).apply {
        this.font = style.font.copy()
    }
}

/**
 * Get a copy of the text button style.
 */
fun Skin.getTextButtonStyle(): TextButton.TextButtonStyle
{
    val style = this.get(TextButton.TextButtonStyle::class.java)
    return TextButton.TextButtonStyle(style).apply {
        this.font = style.font.copy()
    }
}

/**
 * Get a copy of the select box style.
 */
fun Skin.getSelectBoxStyle(): SelectBox.SelectBoxStyle
{
    val style = this.get(SelectBox.SelectBoxStyle::class.java)
    return SelectBox.SelectBoxStyle(style).apply {
        this.font = generateFont(DEFAULT_FONT_SIZE)
        this.listStyle = List.ListStyle(style.listStyle).apply {
            this.font = style.font.copy()
        }
    }
}

/**
 * Get a copy of the window style.
 */
fun Skin.getWindowStyle(): Window.WindowStyle
{
    val style = this.get(Window.WindowStyle::class.java)
    return Window.WindowStyle(style).apply {
        this.titleFont = style.titleFont.copy()
    }
}

/**
 * Get the default label style.
 */
fun defaultLabelStyle(): Label.LabelStyle
{
    return GdxGame.GAME.skinDefault.getLabelStyle()
}

/**
 * Get the default text button style.
 */
fun defaultTextButtonStyle(): TextButton.TextButtonStyle
{
    return GdxGame.GAME.skinDefault.getTextButtonStyle()
}

/**
 * Get the default select box style.
 */
fun defaultSelectBoxStyle(): SelectBox.SelectBoxStyle
{
    return GdxGame.GAME.skinDefault.getSelectBoxStyle()
}

/**
 * Get the default window style.
 */
fun defaultWindowStyle(): Window.WindowStyle
{
    return GdxGame.GAME.skinDefault.getWindowStyle()
}