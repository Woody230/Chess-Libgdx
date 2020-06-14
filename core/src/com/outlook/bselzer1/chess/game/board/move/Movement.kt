package com.outlook.bselzer1.chess.game.board.move

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Shape2D
import com.outlook.bselzer1.chess.game.board.BoardSize

/**
 * How a piece is able to move in a [direction] at most [cap] times inside the space of [container] given [flags].
 */
data class Movement(val direction: Direction, val cap: Int, val container: Shape2D, val flags: Collection<MovementFlag> = emptyList())
{
    constructor(direction: Direction, cap: Int, boardSize: BoardSize, flags: Collection<MovementFlag> = emptyList())
            : this(direction, cap, Rectangle(0f, 0f, (boardSize.columnCount - 1).toFloat(), (boardSize.rowCount - 1).toFloat()), flags)
}