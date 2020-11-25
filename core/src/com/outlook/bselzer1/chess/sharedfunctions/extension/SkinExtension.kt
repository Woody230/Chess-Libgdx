package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.List

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
        this.font = style.font.copy()
        this.listStyle = List.ListStyle(style.listStyle).apply {
            this.font = style.listStyle.font.copy()
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