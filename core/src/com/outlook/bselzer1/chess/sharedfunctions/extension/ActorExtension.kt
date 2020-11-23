package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.scenes.scene2d.Actor
import com.outlook.bselzer1.chess.ui.GdxGame

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
    val vector = GdxGame.GAME.camera.position
    centerOn(vector.x, vector.y)
}

/**
 * Center the actor on a position.
 */
fun Actor.centerOn(x: Number, y: Number)
{
    this.setPosition(x.toFloat() - (this.width / 2), y.toFloat() - (this.height / 2))
}