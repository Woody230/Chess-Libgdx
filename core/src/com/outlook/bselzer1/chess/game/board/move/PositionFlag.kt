package com.outlook.bselzer1.chess.game.board.move

/**
 * The flags associated with retrieving positions.
 */
enum class PositionFlag
{
    /**
     * Skip problematic checks to avoid recursion.
     */
    SKIP_CHECK,

    /**
     * Validate the positions after retrieving them.
     */
    VALIDATE,
}