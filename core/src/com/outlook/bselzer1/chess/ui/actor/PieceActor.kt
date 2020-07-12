package com.outlook.bselzer1.chess.ui.actor

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Disposable
import com.outlook.bselzer1.chess.game.piece.Piece

/**
 * The [Piece] ui
 */
class PieceActor(piece: Piece<*>) : Image(), Disposable
{
    /**
     * The associated [Piece]
     */
    var piece: Piece<*>? = piece

    /**
     * The id of the associated [Piece]
     */
    fun getAssociatedId(): Int?
    {
        return piece?.getId()
    }

    override fun draw(batch: Batch, parentAlpha: Float)
    {
        if (piece == null)
        {
            return
        }

        //TODO static texture factory to retrieve from based on piece name and color -- set texture on self -- need to dispose of the textures via GdxGame dispose

        super.draw(batch, parentAlpha)
    }

    override fun dispose()
    {
        //TODO dispose if needed
    }
}