package com.outlook.bselzer1.chess.ui.screen.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.game.ai.Difficulty
import com.outlook.bselzer1.chess.sharedfunctions.extension.addTo
import com.outlook.bselzer1.chess.sharedfunctions.extension.standardPad
import com.outlook.bselzer1.chess.ui.actor.button.CancelButton
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxDialog
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTable
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTextButton
import com.outlook.bselzer1.chess.ui.screen.settings.SettingsScreen

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
                dialog.show(stage)
            }
        })
    }
}