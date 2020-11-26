package com.outlook.bselzer1.chess.ui.gdx

import com.badlogic.gdx.utils.viewport.ScreenViewport

/**
 * Base viewport.
 */
open class GdxViewport : ScreenViewport()
{
    override fun update(screenWidth: Int, screenHeight: Int, centerCamera: Boolean)
    {
        super.update(screenWidth, screenHeight, centerCamera)

        //Sizes are based on viewport size so the skin must be reinitialized.
        GdxCompanion.SKIN.setup()
    }
}