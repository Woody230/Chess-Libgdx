package com.outlook.bselzer1.chess.ui.screen.game

import com.badlogic.gdx.graphics.g2d.Batch
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.sharedfunctions.extension.centerOnCamera
import com.outlook.bselzer1.chess.sharedfunctions.extension.toDisplayableString
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxDialog
import com.outlook.bselzer1.chess.ui.screen.main.MainMenuScreen

/**
 * Displays the player who won the game.
 */
class VictoryDialog(victor: PlayerColor) : GdxDialog("Game Ended")
{
    init
    {
        isMovable = false

        text("${victor.toDisplayableString()} wins!")
        button("Back")
    }

    override fun draw(batch: Batch?, parentAlpha: Float)
    {
        centerOnCamera()
        super.draw(batch, parentAlpha)
    }

    override fun result(`object`: Any?)
    {
        GdxCompanion.GAME.screen = MainMenuScreen()
    }
}
