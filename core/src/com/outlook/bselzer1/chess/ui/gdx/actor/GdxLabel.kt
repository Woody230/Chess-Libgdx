package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.sharedfunctions.UiStandard
import kotlin.math.roundToInt

/**
 * Standard label.
 */
open class GdxLabel(message: String) : Label(message, GdxCompanion.SKIN), IGdxActor<GdxLabel>
{
    init
    {
        init()
    }

    companion object
    {
        val UI_STANDARD = object : UiStandard()
        {
            override fun getStandardFontSize(): Int = (GdxTextButton.UI_STANDARD.getStandardFontSize() / 1.5).roundToInt()
        }
    }

    override fun getUiStandard(): UiStandard
    {
        return UI_STANDARD
    }
}