package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.outlook.bselzer1.chess.ui.sharedfunctions.GameColor

/**
 * Clear using [color]
 */
fun GL20.glClearColor(color: Color)
{
    this.glClearColor(color.r, color.g, color.b, color.a)
}

/**
 * Clear using the value of [gameColor]
 */
fun GL20.glClearColor(gameColor: GameColor)
{
    this.glClearColor(gameColor.color)
}

/**
 * Render the [gameColor] by clearing
 */
fun GL20.renderBackgroundColor(gameColor: GameColor)
{
    this.glClearColor(gameColor)
    this.glClear(GL20.GL_COLOR_BUFFER_BIT)
}