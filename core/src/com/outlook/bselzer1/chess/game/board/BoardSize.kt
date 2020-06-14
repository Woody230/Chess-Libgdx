package com.outlook.bselzer1.chess.game.board

class BoardSize(val rowCount: Int, val columnCount: Int)
{
    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BoardSize

        if (rowCount != other.rowCount) return false
        if (columnCount != other.columnCount) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = rowCount
        result = 31 * result + columnCount
        return result
    }

    override fun toString(): String
    {
        return "BoardSize(rowCount=$rowCount, columnCount=$columnCount)"
    }
}
