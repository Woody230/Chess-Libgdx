package com.outlook.bselzer1.chess.game.piece.extend

import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.move.Direction
import com.outlook.bselzer1.chess.game.board.move.Movement
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor

/**
 * Moves along the diagonals.
 */
class Bishop(color: PlayerColor, position: Position) : Piece(PieceName.BISHOP, color, position)
{
    override fun getValidPositions(board: Board): Collection<Position>
    {
        //TODO optimize Movement usage in all extended -- set container size on constructor

        return getValidPositions(board,
                Movement(Direction.LEFT1_UP1, Int.MAX_VALUE, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1_UP1, Int.MAX_VALUE, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1_DOWN1, Int.MAX_VALUE, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.LEFT1_DOWN1, Int.MAX_VALUE, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG)
        )
    }
}