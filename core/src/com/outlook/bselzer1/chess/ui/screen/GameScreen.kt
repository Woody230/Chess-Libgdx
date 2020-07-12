package com.outlook.bselzer1.chess.ui.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.BoardName
import com.outlook.bselzer1.chess.sharedfunctions.extension.renderBackgroundColor
import com.outlook.bselzer1.chess.ui.GdxGame
import com.outlook.bselzer1.chess.ui.actor.board.BoardActor
import com.outlook.bselzer1.chess.ui.sharedfunctions.CameraGestureListener
import com.outlook.bselzer1.chess.ui.sharedfunctions.GameColor


/**
 * The game screen.
 */
class GameScreen(game: GdxGame, boardName: BoardName) : Screen
{
    //TODO center camera on the board

    /**
     * The camera.
     */
    private val camera: OrthographicCamera = OrthographicCamera()

    /**
     * The camera gesture listener.
     */
    private val cameraGestureListener: CameraGestureListener = CameraGestureListener(camera)

    /**
     * The viewport for the camera.
     */
    private val viewport: Viewport

    /**
     * The stage.
     */
    private val stage: Stage

    /**
     * The game board.
     */
    private val board: Board = boardName.createBoard().apply { initializePieces() }

    /**
     * The game board actor.
     */
    private val boardActor: BoardActor

    init
    {
        viewport = ScreenViewport(camera)
        stage = Stage(viewport, game.batch)

        //Create after the viewport is applied to use default scaling.
        boardActor = BoardActor.createActor(board, camera)
    }

    override fun hide()
    {

    }

    override fun show()
    {
        val input = InputMultiplexer()
        input.addProcessor(stage)
        input.addProcessor(GestureDetector(CameraGestureListener(camera)))
        Gdx.input.inputProcessor = input

        stage.addActor(boardActor)
    }

    override fun render(delta: Float)
    {
        handleInput()

        Gdx.gl.renderBackgroundColor(GameColor.DEFAULT_BACKGROUND)

        stage.act()
        stage.draw()
    }

    override fun pause()
    {

    }

    override fun resume()
    {

    }

    override fun resize(width: Int, height: Int)
    {
        viewport.update(width, height, true)
    }

    override fun dispose()
    {
        boardActor.dispose()
    }

    /**
     * Handle player input.
     */
    private fun handleInput()
    {
        //TODO key bindings
        //TODO sensitivity

        //Zoom
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1))
        {
            camera.zoom += cameraGestureListener.zoomAmount
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2))
        {
            camera.zoom -= cameraGestureListener.zoomAmount
        }

        camera.zoom = MathUtils.clamp(camera.zoom, cameraGestureListener.minZoom, cameraGestureListener.maxZoom)

        //Move
        if (Gdx.input.isTouched)
        {
            camera.translate(-Gdx.input.deltaX * camera.zoom, Gdx.input.deltaY * camera.zoom)
        }

        val movement = camera.zoom * 10
        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            camera.translate(-movement, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            camera.translate(movement, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S))
        {
            camera.translate(0f, -movement, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W))
        {
            camera.translate(0f, movement, 0f)
        }

        //Rotate
        if (Gdx.input.isKeyPressed(Input.Keys.Q))
        {
            camera.rotate(-.5f, 0f, 0f, 1f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E))
        {
            camera.rotate(.5f, 0f, 0f, 1f)
        }
    }
}