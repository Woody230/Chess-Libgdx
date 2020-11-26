package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.outlook.bselzer1.chess.sharedfunctions.extension.getStandardPadding

/**
 * Standard table.
 */
open class GdxTable : Table()
{
    /**
     * Sets the standard padding in all directions.
     */
    fun standardPad(): GdxTable
    {
        pad(getStandardPadding())
        return this
    }
}