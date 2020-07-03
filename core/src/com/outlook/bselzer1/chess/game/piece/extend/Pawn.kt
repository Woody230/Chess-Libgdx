package com.outlook.bselzer1.chess.game.piece.extend

import com.badlogic.gdx.math.Rectangle
import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.move.Direction
import com.outlook.bselzer1.chess.game.board.move.Movement
import com.outlook.bselzer1.chess.game.board.move.MovementFlag
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.game.piece.promotion.Promotion
import com.outlook.bselzer1.chess.game.piece.promotion.PromotionCondition
import com.outlook.bselzer1.chess.sharedfunctions.extension.addNoNull
import com.outlook.bselzer1.chess.sharedfunctions.extension.addVarargs
import com.outlook.bselzer1.chess.sharedfunctions.extension.isOneOf
import kotlin.math.abs

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
class Pawn(color: PlayerColor, position: Position, board: Board) : Piece<Pawn>(PieceName.PAWN, color, position, board, createPromotion(color, board))
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

    companion object
    {
        /**
         * @return [Pawn] promotion to [Bishop], [Knight], [Rook], or [Queen] with a zone of the last row on the opposite side of the board
         */
        fun createPromotion(color: PlayerColor, board: Board): Promotion
        {
            val promotionRow = if (color == board.topColor) 0 else board.size.rowCount - 1
            return Promotion(Rectangle(0f, promotionRow.toFloat(), (board.size.columnCount - 1).toFloat(), 1f),
                    listOf(PromotionCondition.END_IN_ZONE),
                    listOf(PieceName.BISHOP, PieceName.KNIGHT, PieceName.ROOK, PieceName.QUEEN)
            )
        }
    }

    override fun getPositions(): MutableCollection<Position>
    {
        val collection = super.getPositions()
        collection.addNoNull(getEnPassantPosition())
        return collection
    }

    override fun createCopy(): Pawn
    {
        return Pawn(color, position, board)
    }

    /**
     * @return the en passant capture if it exists
     */
    private fun getEnPassantPosition(): Position?
    {
        val move = board.getLastMove() ?: return null

        //Stop processing if the piece to capture is not valid.
        //Piece to capture must be an enemy, has not moved, and used the initial 2 movement.
        if (this.isAlly(move.fromPiece) || move.fromPiece.hasMoved() || abs(move.fromPosition.y - move.toPosition.y) != 2)
        {
            return null
        }

        //Stop processing if this piece can not capture from the current position.
        //This piece must be in an adjacent column and in the same row as the piece to capture.
        if (abs(this.position.x - move.toPosition.x) != 1 || this.position.y != move.toPosition.y)
        {
            return null
        }

        return Position(move.fromPosition.x, move.fromPosition.y + if (move.fromPiece.color == board.topColor) -1 else 1)
    }
}