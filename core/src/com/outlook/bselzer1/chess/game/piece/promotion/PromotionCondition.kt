package com.outlook.bselzer1.chess.game.piece.promotion

/**
 * Conditions for promoting a piece.
 */
enum class PromotionCondition
{
    /**
     * Piece moves into the promotion zone.
     */
    END_IN_ZONE,

    /**
     * Piece moves from the promotion zone.
     */
    START_IN_ZONE
}