package com.outlook.bselzer1.chess.ui.sharedfunctions

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Array
import com.outlook.bselzer1.chess.sharedfunctions.extension.toDisplayableString

/**
 * The display types.
 */
enum class DisplayType
{
    FULLSCREEN,
    BORDERLESS_FULLSCREEN,
    WINDOWED;

    override fun toString(): String
    {
        return toDisplayableString()
    }

    companion object
    {
        /**
         * Parses the display type from a string.
         *
         * @param type the display type in string form
         * @return the display type if it can be parsed
         */
        fun getDisplayType(type: String): DisplayType?
        {
            return values().firstOrNull { displayType -> displayType.toString() == type }
        }

        /**
         * All of the display types available to the device.
         */
        val DEVICE_DISPLAY_TYPES: Array<DisplayType>
            get()
            {
                val displayTypes: Array<DisplayType>
                if (Gdx.graphics.supportsDisplayModeChange())
                {
                    displayTypes = Array(values())
                }
                else
                {
                    displayTypes = Array()
                    displayTypes.add(FULLSCREEN)
                }

                return displayTypes
            }

        /**
         * The window's current display type.
         */
        val CURRENT_DISPLAY_TYPE: DisplayType
            get()
            {
                return if (Gdx.graphics.isFullscreen) BORDERLESS_FULLSCREEN else WINDOWED

                //TODO borderless fullscreen
                /*
                if(Gdx.graphics.isFullscreen())
                {
                    return FULLSCREEN;
                }

                return UserInterface.isUndecorated() ? BORDERLESS_FULLSCREEN : WINDOWED;
                */
            }
    }
}