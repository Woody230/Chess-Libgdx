package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.outlook.bselzer1.chess.ui.sharedfunctions.UiStandard

/**
 * Standard table.
 */
open class GdxTable : Table(), IGdxActor<GdxTable>
{
    init
    {
        init()
    }

    companion object
    {
        val UI_STANDARD = UiStandard()
    }

    override fun getUiStandard(): UiStandard
    {
        return UI_STANDARD
    }

    /**
     * Sets the standard padding in all directions.
     */
    fun standardPad(): GdxTable
    {
        pad(UI_STANDARD.getStandardPadding())
        return this
    }
}