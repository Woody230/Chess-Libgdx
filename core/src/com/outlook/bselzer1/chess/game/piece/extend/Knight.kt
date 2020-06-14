package com.outlook.bselzer1.chess.game.piece.extend

import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.move.Direction
import com.outlook.bselzer1.chess.game.board.move.Movement
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor

/**
 * Moves either one square horizontally and two squares vertically OR two squares horizontally and one square vertically.
 */
class Knight(color: PlayerColor, position: Position) : Piece(PieceName.KNIGHT, color, position)
{
    override fun getValidPositions(board: Board): Collection<Position>
    {
        return getValidPositions(board,
                Movement(Direction.LEFT2_UP1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.LEFT1_UP2, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1_UP2, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT2_UP1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT2_DOWN1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1_DOWN2, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.LEFT1_DOWN2, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.LEFT2_DOWN1, 1, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG)
        )
    }
}