package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import kotlin.math.roundToInt

/**
 * Standard label.
 */
open class GdxLabel(message: String) : Label(message, GdxCompanion.SKIN.labelStyle), IGdxActor<Label.LabelStyle>
{
    companion object
    {
        val LARGE_FONT_SIZE: Int
            get()
            {
                return (GdxCompanion.CAMERA.viewportHeight / 18).roundToInt()
            }

        val STANDARD_FONT_SIZE: Int
            get()
            {
                return (GdxCompanion.CAMERA.viewportHeight / 28).roundToInt()
            }
    }
}