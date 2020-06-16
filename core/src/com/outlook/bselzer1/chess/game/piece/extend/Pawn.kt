package com.outlook.bselzer1.chess.game.piece.extend

import com.outlook.bselzer1.chess.extension.addVarargs
import com.outlook.bselzer1.chess.extension.isOneOf
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
class Pawn(color: PlayerColor, position: Position, board: Board) : Piece(PieceName.PAWN, color, position, board)
{
    override var hasMoved: Boolean = false
        get() = super.hasMoved
        set(value)
        {
            //Once true, never overwrite.
            if (field || !value)
            {
                return
            }

            field = value

            //Remove the initial 2 square movement once this piece has moved.
            movements.filter { movement -> movement.direction.isOneOf(Direction.DOWN1, Direction.UP1) }
                    .forEach { movement -> movement.cap = 1 }
        }

    init
    {
        if (color == board.topColor)
        {
            movements.addVarargs(
                    Movement(Direction.DOWN1, 2, board.size, listOf(MovementFlag.BLOCKABLE)),
                    Movement(Direction.LEFT1_DOWN1, 1, board.size, listOf(MovementFlag.BLOCKABLE_AFTER_CAPTURE, MovementFlag.MUST_CAPTURE)),
                    Movement(Direction.RIGHT1_DOWN1, 1, board.size, listOf(MovementFlag.BLOCKABLE_AFTER_CAPTURE, MovementFlag.MUST_CAPTURE))
            )
        }
        else
        {
            movements.addVarargs(
                    Movement(Direction.UP1, 2, board.size, listOf(MovementFlag.BLOCKABLE)),
                    Movement(Direction.LEFT1_UP1, 1, board.size, listOf(MovementFlag.BLOCKABLE_AFTER_CAPTURE, MovementFlag.MUST_CAPTURE)),
                    Movement(Direction.RIGHT1_UP1, 1, board.size, listOf(MovementFlag.BLOCKABLE_AFTER_CAPTURE, MovementFlag.MUST_CAPTURE))
            )
        }
    }

    override fun getValidPositions(): Collection<Position>
    {
        //TODO en passant

        return super.getValidPositions()
    }
}