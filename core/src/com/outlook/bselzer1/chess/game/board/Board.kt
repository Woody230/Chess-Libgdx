package com.outlook.bselzer1.chess.game.board

import com.outlook.bselzer1.chess.game.board.move.Move
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.sharedfunctions.extension.addNoNull
import com.outlook.bselzer1.chess.sharedfunctions.extension.copy

/**
 * A chess board.
 * @property size the size of the board
 */
abstract class Board(val size: BoardSize, val topColor: PlayerColor, val bottomColor: PlayerColor)
{
    /**
     * The collection of pieces.
     */
    private val pieces: MutableCollection<Piece<*>> = sortedSetOf(Comparator { a, b ->
        //Comparator needs to handle nulls to avoid exception on trying to remove a null element.
        compareValues(a?.getId(), b?.getId())
    })

    /**
     * The collection of previous moves.
     */
    private val moveHistory: MutableCollection<Move> = mutableListOf()

    /**
     * Initializes the pieces on the board.
     */
    abstract fun initializePieces()

    /**
     * Resets the pieces and move history.
     */
    fun clear()
    {
        pieces.clear()
        moveHistory.clear()
    }

    /**
     * Move a piece at the position [fromPosition] to the position [toPosition].
     */
    fun move(fromPosition: Position, toPosition: Position)
    {
        val fromPiece = pieces.firstOrNull { piece -> piece.position == fromPosition }
                ?: throw KotlinNullPointerException("Unable to retrieve the piece at $fromPosition.")
        val toPiece = pieces.firstOrNull { piece -> piece.position == toPosition }

        pieces.remove(toPiece)
        fromPiece.position = toPosition
        moveHistory.add(Move(fromPosition, toPosition, fromPiece, toPiece))
    }

    /**
     * Add a [piece] to the collection.
     */
    protected open fun addPiece(piece: Piece<*>)
    {
        pieces.add(piece)
    }

    /**
     * @return the piece at [position] if it exists
     */
    fun getPieceAt(position: Position): Piece<*>?
    {
        return pieces.firstOrNull { piece -> piece.position == position }?.copy()
    }

    /**
     * @return the last move previously played
     */
    fun getLastMove(): Move?
    {
        return moveHistory.lastOrNull()?.copy()
    }

    /**
     * @param onlyValidPositions whether or not to use [Piece.getValidPositions] or [Piece.getPositions]
     * @return whether or not a player is in check
     */
    protected abstract fun isInCheck(color: PlayerColor, onlyValidPositions: Boolean): Boolean

    /**
     * @return whether or not a player is in check
     */
    fun isInCheck(color: PlayerColor): Boolean
    {
        return isInCheck(color, true)
    }

    /**
     * @return whether or not a player will be in check given [piece] moves to [newPosition]
     */
    fun willBeInCheck(piece: Piece<*>, newPosition: Position): Boolean
    {
        //Pretend the move is done.
        //A copy is used instead of setting the position to avoid consequences such as changing hasMoved.
        //MUST do removals first since the copy has the same id.
        val copy = piece.copy()
        val capture = pieces.firstOrNull { it.position == newPosition }
        pieces.remove(piece)
        pieces.remove(capture)
        pieces.add(copy)
        copy.position = newPosition

        //Undo the move.
        val check = isInCheck(piece.color, false)
        pieces.remove(copy)
        pieces.add(piece)
        pieces.addNoNull(capture)

        return check
    }

    /**
     * @return whether or not a player is checkmated
     */
    abstract fun isCheckmated(color: PlayerColor): Boolean

    /**
     * @return the pieces
     */
    fun getPieces(): Collection<Piece<*>>
    {
        return pieces.copy()
    }

    /**
     * Replaces [piece] with a new piece of [type].
     */
    fun promotePiece(piece: Piece<*>, type: PieceName)
    {
        if (piece.promotion == null || !piece.promotion.isEligible() || !piece.promotion.successors.contains(type))
        {
            throw UnsupportedOperationException("Unable to promote $piece into $type.")
        }

        pieces.remove(piece)
        pieces.add(type.promoteFrom(piece))
    }
}
