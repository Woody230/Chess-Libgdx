package com.outlook.bselzer1.chess.game.board

import com.outlook.bselzer1.chess.game.board.move.Move
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PlayerColor

/**
 * A chess board.
 * @property size the size of the board
 */
abstract class Board(val size: BoardSize, val topColor: PlayerColor, val bottomColor: PlayerColor)
{
    /**
     * The collection of pieces.
     */
    private val pieces: MutableCollection<Piece<*>> = mutableListOf()

    /**
     * The collection of previous moves.
     */
    private val moveHistory: MutableCollection<Move> = mutableListOf()

    /**
     * Initializes the pieces on the board.
     */
    abstract fun initializePieces()

    /**
     * Add a [piece] to the collection.
     */
    protected fun addPiece(piece: Piece<*>)
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
}
