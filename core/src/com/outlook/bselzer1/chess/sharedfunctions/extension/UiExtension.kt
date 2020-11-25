package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.math.MathUtils
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import kotlin.math.roundToInt


/**
 * @return the button width
 */
fun buttonWidth(): Float
{
    return GdxCompanion.CAMERA.viewportWidth / 7
}

/**
 * @return the button height
 */
fun buttonHeight(): Float
{
    return GdxCompanion.CAMERA.viewportHeight / 10
}

/**
 * @return the button padding
 */
fun buttonPad(): Float
{
    return GdxCompanion.CAMERA.viewportHeight / 25
}

/**
 * @return the button font size
 */
fun buttonFontSize(): Int
{
    val height = buttonHeight()
    return MathUtils.floor(height / 2)
}

/**
 * @return the label font size
 */
fun labelFontSize(): Int
{
    return (buttonFontSize() / 1.5).roundToInt()
}