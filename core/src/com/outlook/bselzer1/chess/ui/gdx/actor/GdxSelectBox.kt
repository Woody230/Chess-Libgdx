package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion

/**
 * Standard select box.
 */
open class GdxSelectBox<Value> : SelectBox<Value>(GdxCompanion.SKIN), IGdxActor<GdxSelectBox<Value>>
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