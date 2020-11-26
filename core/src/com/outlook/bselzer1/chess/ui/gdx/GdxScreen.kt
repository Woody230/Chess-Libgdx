package com.outlook.bselzer1.chess.ui.gdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.outlook.bselzer1.chess.sharedfunctions.extension.renderBackgroundColor
import com.outlook.bselzer1.chess.ui.sharedfunctions.GameColor

/**
 * Base screen.
 */
abstract class GdxScreen : Screen
{
    override fun show()
    {
        GdxCompanion.setupStage()
        Gdx.input.inputProcessor = GdxCompanion.STAGE
    }

    /**
     * Render the background and stage.
     *
     * @param delta the time in seconds since the last render.
     */
    override fun render(delta: Float)
    {
        Gdx.gl20.renderBackgroundColor(GameColor.DEFAULT_BACKGROUND)
        GdxCompanion.STAGE.apply {
            act()
            draw()
        }

        handleInput()
    }

    /**
     * Handle player input.
     */
    abstract fun handleInput()

    /**
     * Resize the viewport and recreate the layout of the stage.
     *
     * @param width  the new width
     * @param height the new height
     */
    override fun resize(width: Int, height: Int)
    {
        GdxCompanion.VIEWPORT.update(width, height, true)
    }

    override fun pause()
    {
    }

    override fun resume()
    {
    }

    override fun hide()
    {
    }

    override fun dispose()
    {
    }
}