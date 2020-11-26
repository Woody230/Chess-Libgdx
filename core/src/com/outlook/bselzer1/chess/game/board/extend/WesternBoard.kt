package com.outlook.bselzer1.chess.game.board.extend

import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.BoardName
import com.outlook.bselzer1.chess.game.board.BoardSize
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.board.move.PositionFlag
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.game.piece.extend.*

/**
 * A Western chess board. 8 x 8
 */
class WesternBoard(topColor: PlayerColor = PlayerColor.WHITE, bottomColor: PlayerColor = PlayerColor.BLACK) : Board(BoardName.WESTERN, BoardSize(8, 8), topColor, bottomColor)
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

        addPiece(Rook(color, Position(0, row), this))
        addPiece(Knight(color, Position(1, row), this))
        addPiece(Bishop(color, Position(2, row), this))
        addPiece(Queen(color, Position(3, row), this))
        addPiece(King(color, Position(4, row), this))
        addPiece(Bishop(color, Position(5, row), this))
        addPiece(Knight(color, Position(6, row), this))
        addPiece(Rook(color, Position(7, row), this))
    }

    /**
     * @return whether or not the [King] is being attacked
     */
    override fun isInCheck(color: PlayerColor, vararg flags: PositionFlag): Boolean
    {
        val pieces = getPieces()
        val king = pieces.first { piece -> piece.name == PieceName.KING && piece.color == color }
        return pieces.any { piece -> piece.color != color && piece.getPositions(*flags, PositionFlag.SKIP_CHECK).contains(king.position) }
    }

    /**
     * @return whether or not there is a valid move left
     */
    override fun isCheckmated(color: PlayerColor): Boolean
    {
        //Validate flag handles king left in check detection
        return getPieces().filter { piece -> piece.color == color }
                .flatMap { piece -> piece.getPositions(PositionFlag.VALIDATE) }
                .isEmpty()
    }
}