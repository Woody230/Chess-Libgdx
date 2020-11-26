package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.outlook.bselzer1.chess.ui.sharedfunctions.UiDirection

/**
 * Set the standard padding in all directions.
 */
fun Cell<Actor>.standardPad(): Cell<Actor>
{
    return pad(actor.getStandardPadding())
}

/**
 * Set the standard padding in particular directions.
 */
fun Cell<Actor>.standardPad(vararg directions: UiDirection): Cell<Actor>
{
    return pad(actor.getStandardPadding(), *directions)
}

/**
 * Sets the small padding in particular directions.
 */
fun Cell<Actor>.smallPad(vararg directions: UiDirection): Cell<Actor>
{
    return pad(actor.getSmallPadding(), *directions)
}

/**
 * Set the padding in particular directions.
 */
private fun Cell<Actor>.pad(pad: Float, vararg directions: UiDirection): Cell<Actor>
{
    directions.forEach { direction ->
        when (direction)
        {
            UiDirection.TOP -> padTop(pad)
            UiDirection.BOTTOM -> padBottom(pad)
            UiDirection.LEFT -> padLeft(pad)
            UiDirection.RIGHT -> padRight(pad)
        }
    }

    return this
}