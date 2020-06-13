package com.outlook.bselzer1.chess.game.piece

/**
 * A chess piece.
 * @property color the associated color
 * @property name the name
 */
abstract class Piece(val color: PlayerColor, val name: PieceName)
{
    override fun toString(): String
    {
        return "Piece(color=$color, name=$name)"
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Piece

        if (color != other.color) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = color.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}