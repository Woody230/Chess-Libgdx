package com.outlook.bselzer1.chess.game.piece.extend

import com.outlook.bselzer1.chess.extension.addVarargs
import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.move.Direction
import com.outlook.bselzer1.chess.game.board.move.Movement
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor

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
class King(color: PlayerColor, position: Position, board: Board) : Piece(PieceName.KING, color, position, board)
{
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

    override fun getValidPositions(): Collection<Position>
    {
        //TODO castling -- remove moves that would put in check

        return super.getValidPositions()
    }
}
