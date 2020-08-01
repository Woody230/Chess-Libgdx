package com.outlook.bselzer1.chess.ui.actor

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
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
        set(value)
        {
            field = value
            if (value == null)
            {
                return
            }

            //Use the cache.
            var pieceTexture = PIECE_TEXTURES.firstOrNull { it.name == value.name && it.color == value.color }
            if (pieceTexture == null)
            {
                pieceTexture = PieceTexture(piece!!.name, piece!!.color)
                PIECE_TEXTURES.add(pieceTexture)
            }

            drawable = TextureRegionDrawable(pieceTexture.texture)
        }

    companion object
    {
        /**
         * The cached textures.
         */
        private val PIECE_TEXTURES = mutableSetOf<PieceTexture>()

        /**
         * Dispose of static variables.
         */
        fun dispose()
        {
            PIECE_TEXTURES.forEach { it.texture.dispose() }
            PIECE_TEXTURES.clear()
        }
    }

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

        super.draw(batch, parentAlpha)
    }

    override fun dispose()
    {

    }
}