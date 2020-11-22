package com.outlook.bselzer1.chess.game.piece

import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.move.Movement
import com.outlook.bselzer1.chess.game.board.move.MovementFlag
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.board.move.PositionFlag
import com.outlook.bselzer1.chess.game.piece.promotion.Promotion
import com.outlook.bselzer1.chess.sharedfunctions.extension.contains
import com.outlook.bselzer1.chess.sharedfunctions.extension.mutableCopy
import com.outlook.bselzer1.chess.sharedfunctions.extension.nextIntId
import com.outlook.bselzer1.chess.sharedfunctions.implement.Copy

//TODO position caching

/**
 * A chess piece.
 * @property name the name
 * @property color the associated color
 * @property position the position
 * @property board the associated board
 */
abstract class Piece<T : Piece<T>>(val name: PieceName, val color: PlayerColor, position: Position, val board: Board, val promotion: Promotion? = null) : Copy<T>
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
            if (field == value)
            {
                return
            }

            promotion?.setEligible(field, value)
            field = value
            hasMoved = true
        }

    /**
     * The identifier.
     */
    private var id: Int = nextIntId()

    /**
     * Whether or not this piece has moved already.
     */
    protected open var hasMoved: Boolean = false

    /**
     * The possible movements.
     */
    protected var movements: MutableCollection<Movement> = mutableListOf()

    override fun toString(): String
    {
        return "Piece(name=$name, color=$color, board=$board, promotion=$promotion, id=$id, hasMoved=$hasMoved, movements=$movements)"
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Piece<*>

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int
    {
        return id
    }

    /**
     * Create a copy of this piece.
     */
    override fun copy(): T
    {
        val piece = createCopy()
        piece.id = this.id
        piece.hasMoved = this.hasMoved
        piece.movements = this.movements.mutableCopy()
        return piece
    }

    /**
     * Create a copy of this piece.
     */
    protected abstract fun createCopy(): T

    /**
     * @return whether or not this piece has moved already
     */
    fun hasMoved(): Boolean
    {
        return hasMoved
    }

    /**
     * @return the identifier
     */
    fun getId(): Int
    {
        return id
    }

    /**
     * @return whether or not [piece] is an ally to this piece
     */
    fun isAlly(piece: Piece<*>): Boolean
    {
        return color == piece.color
    }

    /**
     * @return the positions that this piece can move
     */
    fun getPositions(vararg flags: PositionFlag): MutableCollection<Position>
    {
        val positions = getPositions(movements)
        positions.addAll(getSpecialPositions(*flags))

        if (flags.contains(PositionFlag.VALIDATE))
        {
            //Remove all positions that do not stop or would cause a check
            positions.removeAll { board.willBeInCheck(this, it) }
        }

        return positions
    }

    /**
     * @return positions that do not strictly rely on [movements]
     */
    protected open fun getSpecialPositions(vararg flags: PositionFlag): MutableCollection<Position>
    {
        return mutableListOf()
    }

    /**
     * @return the positions that this piece can move to according to [movements]
     */
    private fun getPositions(movements: Collection<Movement>): MutableCollection<Position>
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
                if (!movement.container.contains(position))
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