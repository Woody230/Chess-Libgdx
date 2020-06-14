package com.outlook.bselzer1.chess.game.piece

import com.outlook.bselzer1.chess.extension.nextIntId
import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.move.Movement
import com.outlook.bselzer1.chess.game.board.move.MovementFlag
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
    companion object
    {
        val BLOCKABLE_AFTER_CAPTURE_FLAG = listOf(MovementFlag.BLOCKABLE_AFTER_CAPTURE)
    }

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
     * @return whether or not [piece] is an ally to this piece
     */
    fun isAlly(piece: Piece): Boolean
    {
        return color == piece.color
    }

    /**
     * @return the valid positions for a piece that is located at [position] given [board]
     */
    abstract fun getValidPositions(board: Board): Collection<Position>

    /**
     * @return whether or not the [newPosition] is a valid move from the current [position] given [board]
     */
    fun isValidPosition(newPosition: Position, board: Board): Boolean
    {
        return getValidPositions(board).contains(newPosition)
    }

    /**
     * @return the valid positions for a piece that can move according to [movements] given [board]
     */
    protected fun getValidPositions(board: Board, vararg movements: Movement): Collection<Position>
    {
        val collection = mutableSetOf<Position>()

        for (movement in movements)
        {
            val position = this.position.copy()
            val flags = movement.flags

            var counter = 0
            while (true)
            {
                //Stop processing when enough moves are generated.
                if (++counter > movement.cap)
                {
                    break
                }

                position.x += movement.direction.xIncrement
                position.y += movement.direction.yIncrement

                //Stop processing when outside of the bounds.
                if (!movement.container.contains(position.x.toFloat(), position.y.toFloat()))
                {
                    break
                }

                val capturing = board.getPieceAt(position)
                if (capturing == null)
                {
                    //Stop processing when the piece must capture but there is no piece.
                    if (flags.contains(MovementFlag.MUST_CAPTURE))
                    {
                        break
                    }

                    //Continue processing when nothing is blocking.
                    collection.add(position.copy())
                    continue
                }

                //Stop processing when the piece can be blocked.
                if (flags.contains(MovementFlag.BLOCKABLE))
                {
                    break
                }

                //Only allow capture of enemy pieces.
                if (!this.isAlly(capturing))
                {
                    collection.add(position.copy())
                }

                //Stop processing when the piece can be blocked.
                if (flags.contains(MovementFlag.BLOCKABLE_AFTER_CAPTURE))
                {
                    break
                }
            }
        }

        return collection
    }
}