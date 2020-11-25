package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion

/**
 * Standard text button.
 */
open class GdxTextButton(message: String) : TextButton(message, GdxCompanion.SKIN), IGdxActor<GdxTextButton>
{
    init
    {
        init()
    }

    override fun getStandardWidth(): Float
    {
        return GdxCompanion.CAMERA.viewportWidth / 7
    }

    override fun getStandardHeight(): Float
    {
        return GdxCompanion.CAMERA.viewportHeight / 10
    }
}