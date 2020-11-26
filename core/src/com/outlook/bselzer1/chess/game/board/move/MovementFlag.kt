package com.outlook.bselzer1.chess.game.board.move

/**
 * The flags associated with movement.
 */
enum class MovementFlag
{
    /**
     * The path can be blocked by another piece but the first piece can be captured.
     */
    BLOCKABLE_AFTER_CAPTURE,

    /**
     * The path can be blocked by another piece.
     */
    BLOCKABLE,

    /**
     * The movement must result in a capture for it to be valid.
     */
    MUST_CAPTURE
}