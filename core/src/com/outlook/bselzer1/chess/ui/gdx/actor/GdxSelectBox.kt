package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import kotlin.math.roundToInt

/**
 * Standard select box.
 */
open class GdxSelectBox<Value> : SelectBox<Value>(GdxCompanion.SKIN.selectBoxStyle)
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