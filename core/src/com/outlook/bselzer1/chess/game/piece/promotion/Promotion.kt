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
     * Whether or not the piece should be able to promote.
     */
    fun isEligible(oldPosition: Position, newPosition: Position): Boolean
    {
        val startInZone = conditions.contains(PromotionCondition.START_IN_ZONE) && zone.contains(oldPosition)
        val endInZone = conditions.contains(PromotionCondition.END_IN_ZONE) && zone.contains(newPosition)
        return startInZone || endInZone
    }
}