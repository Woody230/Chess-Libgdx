package com.outlook.bselzer1.chess.game.board

import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.game.piece.extend.*

/**
 * A Western chess board. 8 x 8
 */
class WesternBoard(topColor: PlayerColor = PlayerColor.WHITE, bottomColor: PlayerColor = PlayerColor.BLACK) : Board(BoardSize(8, 8), topColor, bottomColor)
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
        for (column in 0 until size.columnCount)
        {
            addPiece(Pawn(bottomColor, Position(column, 1)))
            addPiece(Pawn(topColor, Position(column, 6)))
        }
    }

    /**
     * Add all of the pieces to the back row for the associated [color].
     */
    private fun addBackRow(color: PlayerColor)
    {
        val row = if (color == topColor) size.rowCount - 1 else 0

        addPiece(Rook(color, Position(row, 0)))
        addPiece(Knight(color, Position(row, 1)))
        addPiece(Bishop(color, Position(row, 2)))
        addPiece(Queen(color, Position(row, 3)))
        addPiece(King(color, Position(row, 4)))
        addPiece(Bishop(color, Position(row, 5)))
        addPiece(Knight(color, Position(row, 6)))
        addPiece(Rook(color, Position(row, 7)))
    }
}