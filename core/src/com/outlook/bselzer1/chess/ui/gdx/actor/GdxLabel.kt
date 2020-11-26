package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import kotlin.math.roundToInt

/**
 * Standard label.
 */
open class GdxLabel(message: String) : Label(message, GdxCompanion.SKIN.labelStyle)
{
    companion object
    {
        val STANDARD_FONT_SIZE: Int
            get()
            {
                return (GdxCompanion.CAMERA.viewportHeight / 36).roundToInt()
            }
    }
}