package com.outlook.bselzer1.chess.game.board

import com.outlook.bselzer1.chess.game.piece.Piece

/**
 * A chess board.
 * @property size the size of the board
 */
abstract class Board(val size: BoardSize)
{
    /**
     * The collection of pieces.
     */
    private val pieces: MutableList<Piece> = mutableListOf()

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
     * Add a [piece] to the collection.
     */
    protected fun addPiece(piece: Piece)
    {
        pieces.add(piece)
    }

    /**
     * @return a read only collection of all of the pieces
     */
    fun getPieces(): Collection<Piece>
    {
        return pieces.toList()
    }
}
