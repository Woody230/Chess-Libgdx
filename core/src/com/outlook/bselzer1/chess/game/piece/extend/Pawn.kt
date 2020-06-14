package com.outlook.bselzer1.chess.game.piece.extend

import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.move.Direction
import com.outlook.bselzer1.chess.game.board.move.Movement
import com.outlook.bselzer1.chess.game.board.move.MovementFlag
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor

/**
 * Moves one square toward the other side of the board.
 * Can move one OR two squares on its first move.
 * Can only capture a piece in an adjacent column in the next row.
 *
 * En passant capture:
 * 1. Capturing pawn on its 5th row
 * 2. Captured pawn must be in an adjacent column
 * 3. Captured pawn must have just used its two square initial move.
 * 4. The capture can ONLY take place the turn after the two square initial move.
 */
class Pawn(color: PlayerColor, position: Position) : Piece(PieceName.PAWN, color, position)
{
    override fun getValidPositions(board: Board): Collection<Position>
    {
        //TODO en passant, initial two move

        return if (color == board.bottomColor) getValidPositions(board,
                Movement(Direction.UP1, 1, board.size, listOf(MovementFlag.BLOCKABLE)),
                Movement(Direction.LEFT1_UP1, 1, board.size, listOf(MovementFlag.BLOCKABLE_AFTER_CAPTURE, MovementFlag.MUST_CAPTURE)),
                Movement(Direction.RIGHT1_UP1, 1, board.size, listOf(MovementFlag.BLOCKABLE_AFTER_CAPTURE, MovementFlag.MUST_CAPTURE)))
        else getValidPositions(board,
                Movement(Direction.DOWN1, 1, board.size, listOf(MovementFlag.BLOCKABLE)),
                Movement(Direction.LEFT1_DOWN1, 1, board.size, listOf(MovementFlag.BLOCKABLE_AFTER_CAPTURE, MovementFlag.MUST_CAPTURE)),
                Movement(Direction.RIGHT1_DOWN1, 1, board.size, listOf(MovementFlag.BLOCKABLE_AFTER_CAPTURE, MovementFlag.MUST_CAPTURE)))
    }
}