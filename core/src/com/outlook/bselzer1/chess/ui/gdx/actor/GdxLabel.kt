package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion

/**
 * Standard label.
 */
open class GdxLabel(message: String) : Label(message, GdxCompanion.SKIN), IGdxActor<GdxLabel>
{
    init
    {
        init()
    }

    //TODO standard width/height when not using tables
    override fun getStandardWidth(): Float
    {
        return width
    }

    override fun getStandardHeight(): Float
    {
        return height
    }
}