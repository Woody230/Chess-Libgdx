package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.sharedfunctions.UiStandard
import kotlin.math.roundToInt

/**
 * Standard text button.
 */
open class GdxTextButton(message: String) : TextButton(message, GdxCompanion.SKIN), IGdxActor<GdxTextButton>
{
    init
    {
        init()
    }

    companion object
    {
        val UI_STANDARD = object : UiStandard()
        {
            override fun getStandardWidth(): Float = GdxCompanion.CAMERA.viewportWidth / 7
            override fun getStandardHeight(): Float = GdxCompanion.CAMERA.viewportHeight / 10
            override fun getStandardFontSize(): Int = (getStandardHeight() / 2).roundToInt()
        }
    }

    override fun getUiStandard(): UiStandard
    {
        return UI_STANDARD
    }
}