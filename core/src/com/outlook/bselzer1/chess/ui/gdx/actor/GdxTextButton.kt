package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.sharedfunctions.BackgroundAppearance
import com.outlook.bselzer1.chess.ui.sharedfunctions.BackgroundAppearance.EXACT
import com.outlook.bselzer1.chess.ui.sharedfunctions.BackgroundAppearance.EXPANDED
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

    /**
     * Pref width/height are called in the super constructor. Consequently the appearance won't be initialized so this is made nullable to make sure this case is handled.
     */
    var appearance: BackgroundAppearance? = EXPANDED

    override fun getPrefWidth(): Float
    {
        return super.getPrefWidth() + when (appearance ?: EXACT)
        {
            EXACT -> 0f
            EXPANDED -> GdxCompanion.CAMERA.viewportWidth * 0.05f
        }
    }

    override fun getPrefHeight(): Float
    {
        return super.getPrefHeight() + when (appearance ?: EXACT)
        {
            EXACT -> 0f
            EXPANDED -> GdxCompanion.CAMERA.viewportHeight * 0.05f
        }
    }
}