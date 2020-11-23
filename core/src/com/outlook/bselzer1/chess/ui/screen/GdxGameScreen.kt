package com.outlook.bselzer1.chess.ui.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.outlook.bselzer1.chess.ui.GdxGame

/**
 * Base screen.
 */
abstract class GdxGameScreen(protected val camera: OrthographicCamera) : Screen
{
    /**
     * The game.
     */
    val game: GdxGame = GdxGame.GAME

    /**
     * The viewport for the camera.
     */
    val viewport: Viewport

    /**
     * The root of the screen.
     */
    val stage: Stage

    init
    {
        game.camera = camera
        viewport = ScreenViewport(camera)
        stage = Stage(viewport, game.batch)
    }
}