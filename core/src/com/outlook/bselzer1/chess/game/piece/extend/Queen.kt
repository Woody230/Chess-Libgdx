package com.outlook.bselzer1.chess.game.piece.extend

import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.move.Direction
import com.outlook.bselzer1.chess.game.board.move.Movement
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.sharedfunctions.extension.addVarargs

/**
 * Moves in any direction, including diagonals.
 */
class Queen(color: PlayerColor, position: Position, board: Board) : Piece<Queen>(PieceName.QUEEN, color, position, board)
{
    init
    {
        movements.addVarargs(
                Movement(Direction.LEFT1_UP1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1_UP1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1_DOWN1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.LEFT1_DOWN1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.UP1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.DOWN1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.LEFT1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG)
        )
    }

    override fun createCopy(): Queen
    {
        return Queen(color, position, board)
    }
}