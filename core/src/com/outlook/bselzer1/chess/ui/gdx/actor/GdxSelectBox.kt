package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.sharedfunctions.UiStandard

/**
 * Standard select box.
 */
open class GdxSelectBox<Value> : SelectBox<Value>(GdxCompanion.SKIN), IGdxActor<GdxSelectBox<Value>>
{
    init
    {
        init()
    }

    companion object
    {
        val UI_STANDARD = object : UiStandard()
        {
            override fun getStandardFontSize(): Int = GdxLabel.UI_STANDARD.getStandardFontSize()
        }
    }

    override fun getUiStandard(): UiStandard
    {
        return UI_STANDARD
    }
}