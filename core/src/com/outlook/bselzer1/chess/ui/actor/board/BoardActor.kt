package com.outlook.bselzer1.chess.ui.actor.board

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Disposable
import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.extend.WesternBoard
import com.outlook.bselzer1.chess.ui.actor.PieceActor

/**
 * The [Board] ui
 * @property board the associated [Board]
 * @property camera the camera to render with. The viewport must already be set.
 */
abstract class BoardActor(protected val board: Board, protected val camera: OrthographicCamera) : Actor(), Disposable
{
    companion object
    {
        /**
         * @return a new actor associated with the [board]
         */
        fun createActor(board: Board, camera: OrthographicCamera): BoardActor
        {
            return when (board)
            {
                is WesternBoard -> WesternActor(board, camera)
                else -> throw UnsupportedOperationException()
            }
        }
    }

    /**
     * The actors for the pieces on the [board]
     */
    private val pieceActors: Collection<PieceActor> = board.getPieces().map { piece -> PieceActor(piece) }

    override fun draw(batch: Batch, parentAlpha: Float)
    {
        super.draw(batch, parentAlpha)

        //In case of promotion or being captured, attempt to retrieve the piece based on the id of the currently stored piece.
        pieceActors.forEach { actor ->
            val id = actor.getAssociatedId()
            actor.piece = if (id == null) null else board.getPieces().firstOrNull { piece -> piece.getId() == id }
            actor.draw(batch, parentAlpha)
        }
    }

    //TODO piece actor size scaling -- abstract method

    override fun dispose()
    {
        pieceActors.forEach { actor -> actor.dispose() }
    }
}