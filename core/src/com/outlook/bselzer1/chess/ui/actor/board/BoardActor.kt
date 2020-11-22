package com.outlook.bselzer1.chess.ui.actor.board

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import com.badlogic.gdx.utils.Disposable
import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.extend.WesternBoard
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.sharedfunctions.extension.containsPoint
import com.outlook.bselzer1.chess.ui.actor.PieceActor
import com.outlook.bselzer3.libgdxlogger.LibgdxLogger

/**
 * The [Board] ui
 * @property board the associated [Board]
 * @property camera the camera to render with. The viewport must already be set.
 */
abstract class BoardActor(protected val board: Board, protected val camera: OrthographicCamera) : Actor(), Disposable
{
    init
    {
        addListeners()
    }

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
     * The actors for the pieces on the [board].
     */
    protected val pieceActors: Collection<PieceActor> = board.getPieces().map { piece -> PieceActor(piece) }

    /**
     * The actor being dragged.
     */
    private var draggedActor: PieceActor? = null

    override fun draw(batch: Batch, parentAlpha: Float)
    {
        super.draw(batch, parentAlpha)

        //TODO dragged actor on top

        pieceActors.forEach { actor ->
            //Drag listener will handle position if the actor is being dragged.
            if (draggedActor != actor)
            {
                //In case of promotion or being captured, attempt to retrieve the piece based on the id of the currently stored piece.
                val id = actor.getAssociatedId()
                actor.piece = if (id == null) null else board.getPieces().firstOrNull { piece -> piece.getId() == id }

                val position = if (actor.piece == null) Vector2(0f, 0f) else getPieceActorUiPosition(actor.piece!!.position)
                actor.setPosition(position.x, position.y)
            }

            actor.draw(batch, parentAlpha)
        }
    }

    /**
     * Get the graphical position of the piece actor based on its board [position].
     */
    abstract fun getPieceActorUiPosition(position: Position): Vector2

    /**
     * Get the board position of the piece based on a graphical position in the world.
     */
    abstract fun getPiecePosition(x: Float, y: Float): Position

    override fun dispose()
    {
        pieceActors.forEach { actor -> actor.dispose() }
    }

    override fun setDebug(enabled: Boolean)
    {
        super.setDebug(enabled)
        pieceActors.forEach { actor -> actor.debug = enabled }
    }

    /**
     * Add input listeners to this actor.
     */
    private fun addListeners()
    {
        addListener(object : DragListener()
        {
            /**
             * Save the actor being dragged.
             */
            override fun dragStart(event: InputEvent?, x: Float, y: Float, pointer: Int)
            {
                val vector = camera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
                draggedActor = pieceActors.firstOrNull { actor -> actor.containsPoint(vector.x, vector.y) }
            }

            /**
             * Set the actor's position.
             */
            override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int)
            {
                //Center actor on the cursor
                val vector = camera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
                draggedActor?.setPosition(vector.x - draggedActor!!.width / 2, vector.y - draggedActor!!.height / 2)
            }

            /**
             * Set the position to the new board position if applicable.
             */
            override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int)
            {
                //Piece was not dragged so nothing to do.
                if (draggedActor == null)
                {
                    return
                }

                val oldPosition = draggedActor!!.piece?.position
                if (oldPosition == null)
                {
                    LibgdxLogger.error("Unable to get the position for actor with piece ${draggedActor!!.piece}")
                    return
                }

                //TODO attempt move method on board to handle validation that this move can actually happen
                val vector = camera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
                val newPosition = getPiecePosition(vector.x, vector.y)
                board.move(oldPosition, newPosition)
                draggedActor = null

                LibgdxLogger.debug("Dragged piece to $newPosition")
            }
        })
    }
}