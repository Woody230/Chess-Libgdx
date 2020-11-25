package com.outlook.bselzer1.chess.ui.screen.settings

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxSelectBox
import com.outlook.bselzer1.chess.ui.sharedfunctions.DisplaySize
import com.outlook.bselzer1.chess.ui.sharedfunctions.Resolution

/**
 * The select box for selecting the screen resolution.
 */
class DisplaySizeSelection : GdxSelectBox<Resolution>()
{
    init
    {
        items = DisplaySize.DEVICE_RESOLUTIONS
        selected = Resolution.CURRENT_RESOLUTION

        addListener(object : ChangeListener()
        {
            override fun changed(event: ChangeEvent, actor: Actor)
            {
                val sb = actor as SelectBox<*>

                val resolution = Resolution(sb.selected.toString())
                val currentResolution: Resolution = Resolution.CURRENT_RESOLUTION
                if (resolution.compareTo(currentResolution) == 0)
                {
                    //Don't set the display again if the resolution has not changed.
                    return
                }

                SettingsScreen.pref.putInteger(SettingsScreen.KEY_WIDTH, resolution.width).putInteger(SettingsScreen.KEY_HEIGHT, resolution.height).flush()
                SettingsScreen.setDisplay(true)
            }
        })
    }
}