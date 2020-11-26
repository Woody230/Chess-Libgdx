package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import kotlin.math.roundToInt

/**
 * Standard text button.
 */
open class GdxTextButton(message: String) : TextButton(message, GdxCompanion.SKIN.textButtonStyle)
{
    companion object
    {
        val STANDARD_FONT_SIZE: Int
            get()
            {
                return (GdxCompanion.CAMERA.viewportHeight / 20).roundToInt()
            }
    }

    override fun getPrefWidth(): Float
    {
        return super.getPrefWidth() + GdxCompanion.CAMERA.viewportWidth * 0.05f
    }

    override fun getPrefHeight(): Float
    {
        return super.getPrefHeight() + GdxCompanion.CAMERA.viewportHeight * 0.05f
    }
}