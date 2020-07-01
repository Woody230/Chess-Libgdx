package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.math.Shape2D
import com.outlook.bselzer1.chess.game.board.move.Position

/**
 * @return whether or not the a position is within a shape
 */
fun Shape2D.contains(position: Position): Boolean
{
    return this.contains(position.x.toFloat(), position.y.toFloat())
}