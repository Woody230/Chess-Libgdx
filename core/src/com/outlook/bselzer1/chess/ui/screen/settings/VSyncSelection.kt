package com.outlook.bselzer1.chess.ui.screen.settings

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.sharedfunctions.extension.booleanAsUiString
import com.outlook.bselzer1.chess.sharedfunctions.extension.toUiString
import com.outlook.bselzer1.chess.sharedfunctions.extension.uiToBoolean
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxSelectBox

/**
 * The select box for selecting how VSync is enabled.
 */
class VSyncSelection : GdxSelectBox<String>()
{
    init
    {
        setItems(*booleanAsUiString())
        selected = SettingsScreen.pref.getBoolean(SettingsScreen.KEY_VSYNC, SettingsScreen.DEFAULT_VSYNC).toUiString()
        isDisabled = !Gdx.graphics.supportsDisplayModeChange()
        addListener(object : ChangeListener()
        {
            override fun changed(event: ChangeEvent, actor: Actor)
            {
                val sb = actor as SelectBox<*>
                SettingsScreen.pref.putBoolean(SettingsScreen.KEY_VSYNC, sb.selected.toString().uiToBoolean()).flush()
                SettingsScreen.setDisplay(false)
            }
        })
    }
}