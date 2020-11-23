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
            var texture = TEXTURES.firstOrNull { texture -> texture.name == value.name && texture.color == value.color }
            if (texture == null)
            {
                texture = PieceTexture(piece!!.name, piece!!.color)
                TEXTURES.add(texture)
            }

            drawable = TextureRegionDrawable(texture)
        }

    companion object
    {
        /**
         * The cached textures.
         */
        private val TEXTURES = mutableSetOf<PieceTexture>()

        /**
         * Dispose of static variables.
         */
        fun dispose()
        {
            TEXTURES.forEach { texture -> texture.dispose() }
            TEXTURES.clear()
        }
    }

    /**
     * The id of the associated [Piece]
     */
    fun getAssociatedId(): Int?
    {
        return piece?.id
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