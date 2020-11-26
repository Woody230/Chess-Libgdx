package com.outlook.bselzer1.chess.game.ai

import com.outlook.bselzer1.chess.sharedfunctions.extension.toDisplayableString

/**
 * The AI difficulty.
 */
enum class Difficulty
{
    EASY,
    MEDIUM,
    HARD;

    override fun toString(): String
    {
        return toDisplayableString()
    }
}