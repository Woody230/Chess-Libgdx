package com.outlook.bselzer1.chess.game.board.extend

import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.BoardSize
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.PieceName
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
            addPiece(Pawn(bottomColor, Position(column, 1), this))
            addPiece(Pawn(topColor, Position(column, 6), this))
        }
    }

    /**
     * Add all of the pieces to the back row for the associated [color].
     */
    private fun addBackRow(color: PlayerColor)
    {
        val row = if (color == topColor) size.rowCount - 1 else 0

        addPiece(Rook(color, Position(row, 0), this))
        addPiece(Knight(color, Position(row, 1), this))
        addPiece(Bishop(color, Position(row, 2), this))
        addPiece(Queen(color, Position(row, 3), this))
        addPiece(King(color, Position(row, 4), this))
        addPiece(Bishop(color, Position(row, 5), this))
        addPiece(Knight(color, Position(row, 6), this))
        addPiece(Rook(color, Position(row, 7), this))
    }

    /**
     * @return whether or not the [King] is being attacked
     */
    override fun isInCheck(color: PlayerColor, onlyValidPositions: Boolean): Boolean
    {
        val pieces = getPieces()
        val king = pieces.first { it.name == PieceName.KING && it.color == color }

        return pieces.any {
            val positions = if (onlyValidPositions) it.getValidPositions() else it.getPositions()
            return@any it.color != color && positions.contains(king.position)
        }
    }

    /**
     * @return whether or not the [King] has a valid move
     */
    override fun isCheckmated(color: PlayerColor): Boolean
    {
        val king = getPieces().first { it.name == PieceName.KING && it.color == color }
        return !king.getValidPositions().any()
    }
}