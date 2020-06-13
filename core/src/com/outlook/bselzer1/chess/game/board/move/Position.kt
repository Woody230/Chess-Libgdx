package com.outlook.bselzer1.chess.game.board.move

class Position(val row: Int, val column: Int)
{
    override fun toString(): String
    {
        return "Position(row=$row, column=$column)"
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (row != other.row) return false
        if (column != other.column) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = row
        result = 31 * result + column
        return result
    }
}