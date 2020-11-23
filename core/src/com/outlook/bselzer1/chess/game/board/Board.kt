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
import com.outlook.bselzer1.chess.sharedfunctions.extension.toDisplayableString

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
        compareValues(a?.id, b?.id)
    })

    /**
     * The collection of previous moves.
     */
    private val moveHistory: MutableCollection<Move> = mutableListOf()

    /**
     * The turn color.
     */
    var turnColor: PlayerColor = topColor
        private set

    /**
     * The function to call upon victory.
     */
    var resolution: ((PlayerColor) -> Unit)? = null

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
     * Validate that a move is able to be made.
     */
    fun isValidMove(fromPosition: Position, toPosition: Position): Boolean
    {
        val fromPiece = getPieceAt(fromPosition)!!
        return fromPiece.color == turnColor && fromPiece.getPositions(PositionFlag.VALIDATE).contains(toPosition)
    }

    /**
     * Validate that a move is able to be made and then perform it if it is valid.
     */
    fun move(fromPosition: Position, toPosition: Position, successor: PieceName?): Boolean
    {
        if (!isValidMove(fromPosition, toPosition))
        {
            return false
        }

        val fromPiece = pieces.firstOrNull { piece -> piece.position == fromPosition }
                ?: throw KotlinNullPointerException("Unable to retrieve the piece at $fromPosition.")

        //Validation succession.
        if (successor != null && (fromPiece.promotion?.isEligible(fromPosition, toPosition) != true || !fromPiece.promotion.successors.contains(successor)))
        {
            throw IllegalStateException("Successor specified: ${successor.toDisplayableString()} but piece should not be promoted.")
        }

        performMove(fromPosition, toPosition)
        {
            //If there is succession, perform the promotion.
            if (successor != null)
            {
                pieces.remove(fromPiece)
                pieces.add(successor.promoteFrom(fromPiece))
            }
        }

        return true
    }

    /**
     * Move a piece at the position [fromPosition] to the position [toPosition].
     */
    private fun performMove(fromPosition: Position, toPosition: Position, resolution: (() -> Unit)? = null)
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
                performMove(oldRookPosition, castlingPosition.newRookPosition)
            }
        }
        else if (fromPiece.name == PieceName.PAWN)
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

        resolution?.invoke()
        resolveMove()
    }

    /**
     * Resolve a successful move.
     */
    private fun resolveMove()
    {
        val nextColor = if (turnColor == topColor) bottomColor else topColor
        if (isCheckmated(nextColor))
        {
            resolution?.invoke(turnColor)
            return
        }

        turnColor = nextColor
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
     * The player color to start with.
     */
    fun startWithPlayer(color: PlayerColor)
    {
        if (moveHistory.isNotEmpty())
        {
            throw IllegalStateException("Trying to modify the turn color when pieces have already moved.")
        }

        turnColor = color
    }
}
