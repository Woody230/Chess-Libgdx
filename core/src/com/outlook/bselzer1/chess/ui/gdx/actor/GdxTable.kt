package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.Table

/**
 * Standard table.
 */
open class GdxTable : Table(), IGdxActor<GdxTable>
{
    init
    {
        init()
    }

    override fun getStandardWidth(): Float
    {
        return width
    }

    override fun getStandardHeight(): Float
    {
        return height
    }

    /**
     * Sets the standard padding in all directions.
     */
    fun standardPad(): GdxTable
    {
        pad(getStandardPadding())
        return this
    }
}