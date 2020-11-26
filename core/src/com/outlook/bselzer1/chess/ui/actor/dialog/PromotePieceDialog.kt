package com.outlook.bselzer1.chess.ui.actor.dialog

import com.badlogic.gdx.graphics.g2d.Batch
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.sharedfunctions.extension.centerOnCamera
import com.outlook.bselzer1.chess.sharedfunctions.extension.getSmallPadding
import com.outlook.bselzer1.chess.sharedfunctions.extension.toDisplayableString

/**
 * The standard dialog for selecting the piece to promote to.
 */
class PromotePieceDialog(piece: Piece<*>) : AwaitResultDialog<PieceName>("Promote Piece")
{
    init
    {
        if (piece.promotion == null)
        {
            throw IllegalStateException("Trying to create a piece promotion dialog for a piece that does not support promotion.")
        }

        isMovable = false
        text("Select the piece to promote to.")

        //Add button for each piece that can be promoted to.
        piece.promotion.successors.forEach { successor -> button(successor.toDisplayableString(), successor) }

        //Set padding since default padding is minimal.
        val pad = getSmallPadding()
        buttonTable.cells.forEachIndexed { index, cell ->
            if (index != 0)
            {
                cell.padLeft(pad)
            }
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float)
    {
        centerOnCamera()
        super.draw(batch, parentAlpha)
    }
}