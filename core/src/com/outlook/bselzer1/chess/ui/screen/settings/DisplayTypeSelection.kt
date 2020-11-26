package com.outlook.bselzer1.chess.ui.screen.settings

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxSelectBox
import com.outlook.bselzer1.chess.ui.sharedfunctions.DisplayType

/**
 * The select box for selecting how the application window should be displayed.
 */
class DisplayTypeSelection : GdxSelectBox<DisplayType>()
{
    init
    {
        items = DisplayType.DEVICE_DISPLAY_TYPES
        selected = DisplayType.CURRENT_DISPLAY_TYPE

        addListener(object : ChangeListener()
        {
            override fun changed(event: ChangeEvent, actor: Actor)
            {
                val sb = actor as SelectBox<*>

                val displayType: DisplayType? = DisplayType.getDisplayType(sb.selected.toString())
                if (displayType == null || displayType == DisplayType.CURRENT_DISPLAY_TYPE)
                {
                    return
                }

                SettingsScreen.pref.putString(SettingsScreen.KEY_DISPLAY_TYPE, displayType.toString()).flush()
                SettingsScreen.setDisplay(true)
            }
        })
    }
}