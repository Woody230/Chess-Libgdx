package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.outlook.bselzer1.chess.ui.GdxGame

/**
 * The position of the cursor within the world through the lens of a camera.
 */
fun worldCursorPosition(): Vector3
{
    return GdxGame.GAME.camera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
}

/**
 * @return the button width
 */
fun buttonWidth(): Float
{
    return GdxGame.GAME.camera.viewportWidth / 7
}

/**
 * @return the button height
 */
fun buttonHeight(): Float
{
    return GdxGame.GAME.camera.viewportHeight / 10
}

/**
 * @return the button padding
 */
fun buttonPad(): Float
{
    return GdxGame.GAME.camera.viewportHeight / 25
}

/**
 * @return the button font size
 */
fun buttonFontSize(): Int
{
    val height = buttonHeight()
    return MathUtils.floor(height / 2)
}