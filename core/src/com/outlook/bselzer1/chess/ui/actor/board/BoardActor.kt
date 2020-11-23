package com.outlook.bselzer1.chess.ui.actor.board

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import com.badlogic.gdx.utils.Disposable
import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.extend.WesternBoard
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.board.move.PositionFlag
import com.outlook.bselzer1.chess.sharedfunctions.extension.centerOnCursor
import com.outlook.bselzer1.chess.sharedfunctions.extension.containsPoint
import com.outlook.bselzer1.chess.sharedfunctions.extension.toDisplayableString
import com.outlook.bselzer1.chess.sharedfunctions.extension.worldCursorPosition
import com.outlook.bselzer1.chess.ui.actor.InvalidEventDialog
import com.outlook.bselzer1.chess.ui.actor.PieceActor
import com.outlook.bselzer3.libgdxlogger.LibgdxLogger

/**
 * The [Board] ui
 * @property board the associated [Board]
 */
abstract class BoardActor(protected val board: Board) : Actor(), Disposable
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
        fun createActor(board: Board): BoardActor
        {
            return when (board)
            {
                is WesternBoard -> WesternActor(board)
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
    protected var draggedActor: PieceActor? = null

    /**
     * The valid positions to highlight when dragging a piece.
     */
    protected lateinit var draggedValidPositions: Collection<Position>

    /**
     * The dialog for showing invalid events.
     */
    private val invalidEventDialog: InvalidEventDialog = InvalidEventDialog()

    override fun draw(batch: Batch, parentAlpha: Float)
    {
        super.draw(batch, parentAlpha)

        pieceActors.filter { actor -> actor != draggedActor }.forEach { actor ->
            //In case of promotion or being captured, attempt to retrieve the piece based on the id of the currently stored piece.
            val id = actor.getAssociatedId()
            actor.piece = if (id == null) null else board.getPieces().firstOrNull { piece -> piece.getId() == id }

            val position = if (actor.piece == null) Vector2(0f, 0f) else getPieceActorUiPosition(actor.piece!!.position)
            actor.setPosition(position.x, position.y)
            actor.draw(batch, parentAlpha)
        }

        //Draw dragged actor last to keep on top.
        //Drag listener will handle position if the actor is being dragged.
        draggedActor?.draw(batch, parentAlpha)
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

    override fun isTouchFocusTarget(): Boolean
    {
        return super.isTouchFocusTarget() || pieceActors.any { actor -> actor.isTouchFocusTarget }
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
                val vector = worldCursorPosition()
                val actor = pieceActors.firstOrNull { actor -> actor.containsPoint(vector.x, vector.y) }
                val piece = actor?.piece

                //Handle no piece selected or wrong colored piece selected.
                if (piece?.color != board.turnColor)
                {
                    draggedActor = null
                    draggedValidPositions = emptyList()

                    //Only show a dialog if a wrong colored piece was selected, not simply empty space.
                    if (piece != null)
                    {
                        invalidEventDialog.setTitle("${board.turnColor.toDisplayableString()}'s turn")
                                .setMessage("It is ${board.turnColor.toDisplayableString()}'s turn.")
                                .show(stage)
                    }

                    return
                }

                //Handle no valid moves.
                draggedValidPositions = piece.getPositions(PositionFlag.VALIDATE)
                if (draggedValidPositions.isEmpty())
                {
                    draggedActor = null
                    invalidEventDialog.setTitle("No valid moves.")
                            .setMessage("This piece does not have a valid move.")
                            .show(stage)
                    return
                }

                draggedActor = actor
            }

            /**
             * Set the actor's position.
             */
            override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int)
            {
                draggedActor?.centerOnCursor()
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

                //TODO piece promotion with dialog

                val vector = worldCursorPosition()
                val newPosition = getPiecePosition(vector.x, vector.y)
                board.attemptMove(oldPosition, newPosition)
                draggedActor = null

                LibgdxLogger.debug("Dragged piece to $newPosition")
            }
        })
    }
}