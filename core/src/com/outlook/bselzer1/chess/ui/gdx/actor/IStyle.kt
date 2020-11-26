package com.outlook.bselzer1.chess.ui.gdx.actor

interface IStyle<Style>
{
    /**
     * Get the associated style.
     */
    fun getStyle(): Style

    /**
     * Set the style.
     */
    fun setStyle(style: Style)
}