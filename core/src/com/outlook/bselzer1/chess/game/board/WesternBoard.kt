package com.outlook.bselzer1.chess.game.board

import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.game.piece.extend.*

/**
 * A Western chess board. 8 x 8
 */
class WesternBoard(private val topColor: PlayerColor = PlayerColor.WHITE, private val bottomColor: PlayerColor = PlayerColor.BLACK) : Board(8, 8)
{
    /**
     * Adds the front row of pawns and back row for both colors.
     */
    override fun initializePieces()
    {
        //Add back row
        addBackRow(bottomColor)
        addBackRow(topColor)

        //Add front row
        for (column in 0 until columnCount)
        {
            addPiece(Position(1, column), Pawn(bottomColor))
            addPiece(Position(6, column), Pawn(topColor))
        }
    }

    /**
     * Add all of the pieces to the back row for the associated [color].
     */
    private fun addBackRow(color: PlayerColor)
    {
        val row = if (color == topColor) rowCount - 1 else 0

        addPiece(Position(row, 0), Rook(color))
        addPiece(Position(row, 1), Knight(color))
        addPiece(Position(row, 2), Bishop(color))
        addPiece(Position(row, 3), Queen(color))
        addPiece(Position(row, 4), King(color))
        addPiece(Position(row, 5), Bishop(color))
        addPiece(Position(row, 6), Knight(color))
        addPiece(Position(row, 7), Rook(color))
    }
}