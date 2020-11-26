package com.outlook.bselzer1.chess.ui.actor.piece

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.outlook.bselzer1.chess.game.piece.Piece

/**
 * The [Piece] ui
 */
class PieceActor(piece: Piece<*>) : Image()
{
    /**
     * The associated [Piece]
     */
    var piece: Piece<*>? = piece
        set(value)
        {
            field = value
            value ?: return

            //Use the cache.
            var texture = TEXTURES.firstOrNull { texture -> texture.name == value.name && texture.color == value.color }
            if (texture == null)
            {
                texture = PieceTexture(value.name, value.color)
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
        piece ?: return
        super.draw(batch, parentAlpha)
    }
}