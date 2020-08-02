package com.outlook.bselzer1.chess.ui.sharedfunctions

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Array

/**
 * The display size.
 */
enum class DisplaySize(width: Int, height: Int)
{
    R_640_480(640, 480),
    R_800x600(800, 600),
    R_1024x600(1024, 600),
    R_1024x768(1024, 768),
    R_1152x864(1152, 864),
    R_1280x720(1280, 720),
    R_1280x800(1280, 800),
    R_1280x1024(1280, 1024),
    R_1360x768(1360, 768),
    R_1366x768(1366, 768),
    R_1440x900(1440, 900),
    R_1536x864(1536, 864),
    R_1600x900(1600, 900),
    R_1680x1050(1680, 1050),
    R_1920x1080(1920, 1080),
    R_1920x1200(1920, 1200),
    R_2560x1080(2560, 1080),
    R_2560x1440(2560, 1440),
    R_3440x1440(3440, 1440),
    R_3840x2160(3840, 2160);

    /**
     * The resolution.
     */
    val resolution: Resolution = Resolution(width, height)

    override fun toString(): String
    {
        return resolution.toString()
    }

    companion object
    {
        /**
         * All of the resolutions available to the device.
         */
        var DEVICE_RESOLUTIONS: Array<Resolution>? = null
            get()
            {
                if (field != null)
                {
                    return field
                }

                val displayMode = Gdx.graphics.displayMode
                val resolutionDisplay = Resolution(displayMode.width, displayMode.height)

                //Add all of the resolutions that fit the current display mode's resolution.
                field = Array()
                if (Gdx.graphics.supportsDisplayModeChange())
                {
                    values().map { displaySize -> displaySize.resolution }
                            .filter { displaySize -> displaySize.fits(resolutionDisplay) }
                            .forEach { displaySize -> field!!.add(displaySize) }
                }

                val currentResolution = Resolution.CURRENT_RESOLUTION
                if (!field!!.contains(currentResolution, false))
                {
                    field!!.add(currentResolution)
                }

                field!!.sort()
                return field
            }
    }
}