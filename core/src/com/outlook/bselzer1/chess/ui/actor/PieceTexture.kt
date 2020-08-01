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
data class PieceTexture(val name: PieceName, val color: PlayerColor)
{
    /**
     * The texture.
     */
    val texture: Texture

    init
    {
        val pixmap = Pixmap(Gdx.files.internal(name.assetLocation))
        pixmap.setColor(color.color)
        pixmap.replacementFill()
        texture = Texture(pixmap)
        pixmap.dispose()
    }
}