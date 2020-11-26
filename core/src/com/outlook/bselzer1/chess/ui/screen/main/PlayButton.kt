package com.outlook.bselzer1.chess.ui.screen.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.game.board.BoardName
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTextButton
import com.outlook.bselzer1.chess.ui.screen.game.GameScreen

/**
 * The button for starting a game.
 */
class PlayButton : GdxTextButton("Play")
{
    init
    {
        addListener(object : ChangeListener()
        {
            override fun changed(event: ChangeEvent, actor: Actor)
            {
                GdxCompanion.GAME.screen = GameScreen(BoardName.WESTERN)

                //TODO ai difficulty selection
                /*
                val dialog = GdxDialog("AI Difficulty").apply {
                    isMovable = false
                }

                //Add difficulty buttons on first row then center the cancel button on the next row.
                val tblRootDialog = GdxTable().apply {
                    debug = SettingsScreen.isDebug()
                    Difficulty.values().forEach { difficulty ->
                        DifficultyButton(difficulty).addTo(this).fillX().standardPad()
                    }
                    row()
                    add()
                    CancelButton(dialog).addTo(this).fillX().standardPad()
                }

                dialog.add(tblRootDialog)
                dialog.show(stage)*/
            }
        })
    }
}