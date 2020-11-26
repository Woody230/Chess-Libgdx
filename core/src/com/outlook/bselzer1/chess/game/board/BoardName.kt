package com.outlook.bselzer1.chess.game.board

import com.outlook.bselzer1.chess.game.board.extend.WesternBoard
import com.outlook.bselzer1.chess.sharedfunctions.extension.toDisplayableString

/**
 * The name of the board.
 */
enum class BoardName
{
    WESTERN;

    /**
     * @return a new board associated with the name
     */
    fun createBoard(): Board
    {
        return when (this)
        {
            WESTERN -> WesternBoard()
        }
    }

    override fun toString(): String
    {
        return toDisplayableString()
    }
}