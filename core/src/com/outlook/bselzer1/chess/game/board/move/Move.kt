package com.outlook.bselzer1.chess.game.board.move

import com.outlook.bselzer1.chess.game.piece.Piece

/**
 * [fromPiece] moves from [fromPosition] to [toPosition] to capture [toPiece] if it exists
 */
class Move(val fromPosition: Position, val toPosition: Position, val fromPiece: Piece, val toPiece: Piece?)
{
    override fun toString(): String
    {
        return "Move(fromPosition=$fromPosition, toPosition=$toPosition, fromPiece=$fromPiece, toPiece=$toPiece)"
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Move

        if (fromPosition != other.fromPosition) return false
        if (toPosition != other.toPosition) return false
        if (fromPiece != other.fromPiece) return false
        if (toPiece != other.toPiece) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = fromPosition.hashCode()
        result = 31 * result + toPosition.hashCode()
        result = 31 * result + fromPiece.hashCode()
        result = 31 * result + (toPiece?.hashCode() ?: 0)
        return result
    }
}