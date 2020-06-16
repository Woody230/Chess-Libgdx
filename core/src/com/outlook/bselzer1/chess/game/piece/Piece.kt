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
 * @property board the associated board
 */
abstract class Piece(val name: PieceName, val color: PlayerColor, position: Position, val board: Board)
{
    companion object
    {
        val BLOCKABLE_AFTER_CAPTURE_FLAG = listOf(MovementFlag.BLOCKABLE_AFTER_CAPTURE)
        const val INFINITE_CAP = Int.MAX_VALUE
    }

    /**
     * The position.
     */
    var position: Position = position
        get() = field.copy()
        set(value)
        {
            if(field == value)
            {
                return
            }

            field = value
            hasMoved = true
        }

    /**
     * The identifier.
     */
    private val id: Int = nextIntId()

    /**
     * Whether or not the piece has moved already.
     */
    open var hasMoved: Boolean = false

    /**
     * The possible movements.
     */
    protected val movements: MutableCollection<Movement> = mutableListOf()

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
     * @return the valid positions for a piece that is located at [position]
     */
    open fun getValidPositions(): Collection<Position>
    {
        return getValidPositions(movements)
    }

    /**
     * @return whether or not the [newPosition] is a valid move from the current [position]
     */
    fun isValidPosition(newPosition: Position): Boolean
    {
        return getValidPositions().contains(newPosition)
    }

    /**
     * @return the valid positions for a piece that can move according to [movements]
     */
    protected fun getValidPositions(movements: Collection<Movement>): Collection<Position>
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