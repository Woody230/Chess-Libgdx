package com.outlook.bselzer1.chess.game.piece.extend

import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.move.*
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.sharedfunctions.extension.addVarargs
import com.outlook.bselzer1.chess.sharedfunctions.extension.toExclusive

/**
 * Moves one square in any direction.
 *
 * Castling:
 * 1. The king and chosen rook are on the first row.
 * 2. The king and chosen rank have not moved.
 * 3. There are no pieces between the king and chosen rook.
 * 4. The king is not and will not be in check.
 * 5. The king does not pass a square which would be in check.
 */
class King(color: PlayerColor, position: Position, board: Board) : Piece<King>(PieceName.KING, color, position, board)
{
    /**
     * The collection of castling positions.
     */
    val castlingPositions = mutableSetOf<CastlingPosition>()

    init
    {
        movements.addVarargs(
                Movement(Direction.LEFT1_UP1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.UP1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1_UP1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1_DOWN1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.DOWN1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.LEFT1_DOWN1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.LEFT1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG)
        )
    }

    /**
     * @return the castling positions if they exist
     */
    override fun getSpecialPositions(vararg flags: PositionFlag): MutableCollection<Position>
    {
        return getCastlingPositions(*flags)
    }

    /**
     * @return the castling positions if they exist
     */
    private fun getCastlingPositions(vararg flags: PositionFlag): MutableCollection<Position>
    {
        val positions = mutableListOf<Position>()

        //Stop processing if the king has already moved or is currently in check.
        if (this.hasMoved || (!flags.contains(PositionFlag.SKIP_CHECK) && board.isInCheck(color)))
        {
            return positions
        }

        //Get all of the player's rooks that have not moved and are in the same row.
        for (rook in board.getPieces().filter { piece -> piece.name == PieceName.ROOK && piece.color == this.color && !piece.hasMoved() && piece.position.y == this.position.y })
        {
            val inBetween = this.position.x toExclusive rook.position.x
            val kingSide = inBetween.take(2)

            //Make sure there are no pieces in between and the king will not pass through or end in check.
            //Need at least two squares in between, which could occur during testing situations.
            if (inBetween.any { x -> board.getPieceAt(Position(x, this.position.y)) != null }
                    || kingSide.size < 2
                    || kingSide.any { x -> board.willBeInCheck(this, Position(x, this.position.y)) }
            )
            {
                continue
            }

            //King's new position is two squares in the direction of the rook.
            //Rook's new position is one square in the direction of the rook.
            val newKingPosition = Position(kingSide.last(), this.position.y)
            positions.add(newKingPosition)
            castlingPositions.add(CastlingPosition(newKingPosition, Position(kingSide.first(), this.position.y), this, rook))
        }

        return positions
    }

    override fun createCopy(): King
    {
        return King(color, position, board)
    }
}
