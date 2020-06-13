package com.outlook.bselzer1.chess.game.board

import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece

/**
 * A chess board.
 * @property rowCount the number of rows
 * @property columnCount the number of columns
 */
abstract class Board(val rowCount: Int, val columnCount: Int)
{
    /**
     * The collection of positions which map to their corresponding piece.
     */
    private val positions: MutableMap<Position, Piece> = mutableMapOf()

    /**
     * Initialize the board.
     */
    init
    {
        this.initializePieces()
    }

    /**
     * Initializes the pieces on the board.
     */
    protected abstract fun initializePieces()

    /**
     * Add a [piece] to the position collection at [position].
     */
    protected fun addPiece(position: Position, piece: Piece)
    {
        positions[position] = piece
    }

    /**
     * Move a [piece] from its position in the position collection to [newPosition].
     * If there is a piece at [newPosition] it is consequently removed.
     */
    protected fun movePiece(newPosition: Position, piece: Piece)
    {
        val oldPosition = positions.filter { it.value == piece }.keys.first()

        positions.remove(oldPosition)
        positions[newPosition] = piece
    }
}
