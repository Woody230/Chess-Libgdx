package com.outlook.bselzer1.chess.game.board

import com.outlook.bselzer1.chess.game.board.move.Move
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.board.move.PositionFlag
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.game.piece.extend.King
import com.outlook.bselzer1.chess.game.piece.extend.Pawn
import com.outlook.bselzer1.chess.sharedfunctions.extension.addNoNull
import com.outlook.bselzer1.chess.sharedfunctions.extension.copy
import com.outlook.bselzer3.libgdxlogger.LibgdxLogger

/**
 * A chess board.
 * @property size the size of the board
 */
abstract class Board(val name: BoardName, val size: BoardSize, val topColor: PlayerColor, val bottomColor: PlayerColor)
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
     * Validate that a move is able to be made and then perform it if it is valid.
     */
    fun attemptMove(fromPosition: Position, toPosition: Position)
    {
        val fromPiece = getPieceAt(fromPosition)!!
        if (!fromPiece.getPositions(PositionFlag.VALIDATE).contains(toPosition))
        {
            LibgdxLogger.debug("Attempted to move from $fromPosition to $toPosition but the position is not valid.")
            return
        }

        move(fromPosition, toPosition)
    }

    //TODO private
    /**
     * Move a piece at the position [fromPosition] to the position [toPosition].
     */
    fun move(fromPosition: Position, toPosition: Position)
    {
        //Must get actual piece, not a copy (which would occur using getPieceAt)
        val fromPiece = pieces.firstOrNull { piece -> piece.position == fromPosition }
                ?: throw KotlinNullPointerException("Unable to retrieve the piece at $fromPosition.")
        var toPiece = pieces.firstOrNull { piece -> piece.position == toPosition }

        if (fromPiece.name == PieceName.KING)
        {
            val king = fromPiece as King
            val castlingPosition = king.castlingPositions.firstOrNull { position -> position.king == king && position.newKingPosition == toPosition }

            //Found a castling position so move the rook.
            if (castlingPosition != null)
            {
                val oldRookPosition = pieces.first { piece -> piece == castlingPosition.rook }.position
                move(oldRookPosition, castlingPosition.newRookPosition)
            }
        }
        else if(fromPiece.name == PieceName.PAWN)
        {
            val pawn = fromPiece as Pawn
            if (pawn.getEnPassantPosition() == toPosition)
            {
                //En passant means that toPiece will be null at this point since toPiece is not at toPosition.
                //Due to the rules of en passant, the piece to capture must be the piece that just moved.
                //Must get actual piece, not a copy (which would occur using getLastMove)
                toPiece = moveHistory.last().fromPiece
            }
        }

        pieces.remove(toPiece)
        fromPiece.position = toPosition
        moveHistory.add(Move(fromPosition, toPosition, fromPiece, toPiece))

        //TODO determine victory abstract method
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
     * @return whether or not a player is in check
     */
    abstract fun isInCheck(color: PlayerColor, vararg flags: PositionFlag): Boolean

    /**
     * @return whether or not a player will be in check given [piece] moves to [newPosition]
     */
    fun willBeInCheck(piece: Piece<*>, newPosition: Position): Boolean
    {
        //Pretend the move is done.
        //A copy is used instead of setting the position to avoid consequences such as changing hasMoved.
        //MUST do removals first since the copy has the same id.
        val copy = piece.copy()
        val capture = pieces.firstOrNull { boardPiece -> boardPiece.position == newPosition }
        pieces.remove(piece)
        pieces.remove(capture)
        pieces.add(copy)
        copy.position = newPosition

        val check = isInCheck(piece.color)

        //Undo the move.
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
