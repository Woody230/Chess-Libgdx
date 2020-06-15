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
 * Moves vertically or horizontally.
 */
class Rook(color: PlayerColor, position: Position, board: Board) : Piece(PieceName.ROOK, color, position, board)
{
    init
    {
        movements.addVarargs(
                Movement(Direction.UP1, Int.MAX_VALUE, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.RIGHT1, Int.MAX_VALUE, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.DOWN1, Int.MAX_VALUE, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG),
                Movement(Direction.LEFT1, Int.MAX_VALUE, board.size, BLOCKABLE_AFTER_CAPTURE_FLAG)
        )
    }
}