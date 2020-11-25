package com.outlook.bselzer1.chess.ui.screen.settings

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTextButton
import com.outlook.bselzer1.chess.ui.screen.main.MainMenuScreen

//TODO android style handling of screens to go back => move to common

/**
 * The button for returning to the main screen.
 */
class BackButton : GdxTextButton("Back")
{
    init
    {
        addListener(object : ChangeListener()
        {
            override fun changed(event: ChangeEvent, actor: Actor)
            {
                GdxCompanion.GAME.screen = MainMenuScreen()
            }
        })
    }
}