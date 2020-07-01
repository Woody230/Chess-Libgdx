package com.outlook.bselzer1.chess.game.piece.promotion

import com.badlogic.gdx.math.Shape2D
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.sharedfunctions.extension.contains

/**
 * How a piece promotes to one of [successors] for a [zone] given [conditions]
 */
data class Promotion(val zone: Shape2D, val conditions: Collection<PromotionCondition>, val successors: Collection<PieceName>)
{
    /**
     * Whether or not the piece is eligible for promotion.
     */
    private var isEligible = false

    /**
     * @return whether or not the piece is eligible for promotion.
     */
    fun isEligible(): Boolean
    {
        return isEligible
    }

    /**
     * Sets whether or not the piece should be able to promote.
     */
    fun setEligible(oldPosition: Position, newPosition: Position)
    {
        val startInZone = conditions.contains(PromotionCondition.START_IN_ZONE) && zone.contains(oldPosition)
        val endInZone = conditions.contains(PromotionCondition.END_IN_ZONE) && zone.contains(newPosition)
        isEligible = startInZone || endInZone
    }
}