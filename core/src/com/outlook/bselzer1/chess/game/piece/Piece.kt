package com.outlook.bselzer1.chess.game.piece

import com.outlook.bselzer1.chess.extension.nextIntId
import com.outlook.bselzer1.chess.game.board.BoardSize
import com.outlook.bselzer1.chess.game.board.move.Position

//TODO position caching

/**
 * A chess piece.
 * @property name the name
 * @property color the associated color
 * @property position the position
 * @property id the identifier
 */
abstract class Piece(val name: PieceName, val color: PlayerColor, val position: Position)
{
    private val id: Int = nextIntId()

    override fun toString(): String
    {
        return "Piece(name=$name, color=$color, position=$position, id=$id)"
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Piece

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int
    {
        return id
    }

    /**
     * @return the valid positions for a piece that is located at [position] for a board with [size]
     */
    abstract fun getValidPositions(size: BoardSize): Collection<Position>

    /**
     * @return whether or not the [newPosition] is a valid move from the current [position] given a board with [size]
     */
    fun isValidPosition(newPosition: Position, size: BoardSize): Boolean
    {
        return getValidPositions(size).contains(newPosition)
    }
}