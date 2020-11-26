package com.outlook.bselzer1.chess.ui.actor.button

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTextButton
import com.outlook.bselzer1.chess.ui.screen.settings.SettingsScreen

/**
 * The button for going to the settings screen.
 */
class SettingsButton : GdxTextButton("Settings")
{
    init
    {
        addListener(object : ChangeListener()
        {
            override fun changed(event: ChangeEvent, actor: Actor)
            {
                GdxCompanion.GAME.screen = SettingsScreen()
            }
        })
    }
}