package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion

/**
 * A point is within the plane of the actor.
 */
fun Actor.containsPoint(x: Number, y: Number): Boolean
{
    return x.isBetweenInclusive(this.x, this.x + width) && y.isBetweenInclusive(this.y, this.y + height)
}

/**
 * Center the actor on the cursor.
 */
fun Actor.centerOnCursor()
{
    val vector = worldCursorPosition()
    centerOn(vector.x, vector.y)
}

/**
 * Center the actor on the camera.
 */
fun Actor.centerOnCamera()
{
    val vector = GdxCompanion.CAMERA.position
    centerOn(vector.x, vector.y)
}

/**
 * Center the actor on a position.
 */
fun Actor.centerOn(x: Number, y: Number)
{
    this.setPosition(x.toFloat() - (this.width / 2), y.toFloat() - (this.height / 2))
}

/**
 * Get the standard padding.
 */
fun Actor.getStandardPadding(): Float
{
    return GdxCompanion.CAMERA.viewportHeight / 25
}

/**
 * Get the small padding.
 */
fun Actor.getSmallPadding(): Float
{
    return getStandardPadding() / 2
}

/**
 * Add the actor to the table.
 */
fun Actor.addTo(table: Table): Cell<Actor>
{
    if (table == this)
    {
        throw IllegalArgumentException("Trying to add a table to itself. Replace argument with the root table.")
    }

    return table.add(this)
}