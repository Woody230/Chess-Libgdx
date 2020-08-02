package com.outlook.bselzer1.chess.ui.actor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.sharedfunctions.extension.replacementFill

/**
 * Abstraction of a texture for a piece.
 */
data class PieceTexture(val name: PieceName, val color: PlayerColor) : Texture(createPixmap(name, color))
{
    companion object
    {
        /**
         * Create a pixmap to match the [color] for the asset associated with the [name].
         */
        private fun createPixmap(name: PieceName, color: PlayerColor): Pixmap
        {
            val pixmap = Pixmap(Gdx.files.internal(name.assetLocation))
            pixmap.setColor(color.color)
            pixmap.replacementFill()
            return pixmap
        }
    }
}