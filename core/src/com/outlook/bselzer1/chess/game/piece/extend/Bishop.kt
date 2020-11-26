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
 * Moves along the diagonals.
 */
class Bishop(color: PlayerColor, position: Position, board: Board) : Piece<Bishop>(PieceName.BISHOP, color, position, board)
{
    init
    {
        movements.addVarargs(
                Movement(Direction.LEFT1_UP1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1_UP1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1_DOWN1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.LEFT1_DOWN1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG)
        )
    }

    override fun createCopy(): Bishop
    {
        return Bishop(color, position, board)
    }
}