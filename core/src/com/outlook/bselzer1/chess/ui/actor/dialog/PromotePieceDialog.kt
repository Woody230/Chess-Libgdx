package com.outlook.bselzer1.chess.ui.actor.dialog

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.sharedfunctions.extension.centerOnCamera
import com.outlook.bselzer1.chess.sharedfunctions.extension.toDisplayableString
import com.outlook.bselzer1.chess.ui.GdxGame

/**
 * The standard dialog for selecting the piece to promote to.
 */
class PromotePieceDialog(piece: Piece<*>, private val resolution: (PieceName) -> Unit) : Dialog("Promote Piece", GdxGame.GAME.skinDefault)
{
    init
    {
        isMovable = false
        isModal = false

        text("Select the piece to promote to:")

        piece.promotion!!.successors.forEach { successor -> button(successor.toDisplayableString(), successor) }
    }

    override fun result(`object`: Any?)
    {
        resolution(`object` as PieceName)
    }

    override fun draw(batch: Batch?, parentAlpha: Float)
    {
        centerOnCamera()
        super.draw(batch, parentAlpha)
    }
}