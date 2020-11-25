package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.sharedfunctions.UiDirection
import com.outlook.bselzer1.chess.ui.sharedfunctions.UiDirection.*

/**
 * Interface for standardizing existing actors.
 */
interface IGdxActor<A : Actor>
{
    /**
     * Initialization.
     */
    fun A.init()
    {
        width = getStandardWidth()
        height = getStandardHeight()
    }

    /**
     * Get the standard width.
     */
    fun getStandardWidth(): Float

    /**
     * Get the standard height.
     */
    fun getStandardHeight(): Float

    /**
     * Get the standard padding.
     */
    fun getStandardPadding(): Float
    {
        return GdxCompanion.CAMERA.viewportHeight / 25
    }

    /**
     * Get the small padding.
     */
    fun getSmallPadding(): Float
    {
        return getStandardPadding() / 2
    }

    /**
     * Add the actor to the table.
     */
    fun A.addTo(table: Table): Cell<A>
    {
        if (table == this)
        {
            throw IllegalArgumentException("Trying to add a table to itself. Replace argument with the root table.")
        }

        return table.add(this)
    }

    /**
     * Set the standard minimum size.
     */
    fun Cell<A>.standardMinSize(): Cell<A>
    {
        return minSize(getStandardWidth(), getStandardHeight())
    }

    /**
     * Set the standard padding in all directions.
     */
    fun Cell<A>.standardPad(): Cell<A>
    {
        return pad(getStandardPadding())
    }

    /**
     * Set the standard padding in particular directions.
     */
    fun Cell<A>.standardPad(vararg directions: UiDirection): Cell<A>
    {
        return pad(getStandardPadding(), *directions)
    }

    /**
     * Sets the small padding in particular directions.
     */
    fun Cell<A>.smallPad(vararg directions: UiDirection): Cell<A>
    {
        return pad(getSmallPadding(), *directions)
    }

    /**
     * Set the padding in particular directions.
     */
    private fun Cell<A>.pad(pad: Float, vararg directions: UiDirection): Cell<A>
    {
        directions.forEach { direction ->
            when (direction)
            {
                TOP -> padTop(pad)
                BOTTOM -> padBottom(pad)
                LEFT -> padLeft(pad)
                RIGHT -> padRight(pad)
            }
        }

        return this
    }
}