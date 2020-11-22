package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * A point is within the plane of the actor.
 */
fun Actor.containsPoint(x: Number, y: Number): Boolean
{
    return x.isBetweenInclusive(this.x, this.x + width) && y.isBetweenInclusive(this.y, this.y + height)
}