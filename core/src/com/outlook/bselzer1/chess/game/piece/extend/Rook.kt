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
 * Moves vertically or horizontally.
 */
class Rook(color: PlayerColor, position: Position, board: Board) : Piece<Rook>(PieceName.ROOK, color, position, board)
{
    init
    {
        movements.addVarargs(
                Movement(Direction.UP1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.DOWN1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.LEFT1, INFINITE_CAP, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG)
        )
    }

    override fun createCopy(): Rook
    {
        return Rook(color, position, board)
    }
}