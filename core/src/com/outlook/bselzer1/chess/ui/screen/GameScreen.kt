package com.outlook.bselzer1.chess.ui.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.BoardName
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.sharedfunctions.extension.*
import com.outlook.bselzer1.chess.ui.actor.board.BoardActor
import com.outlook.bselzer1.chess.ui.sharedfunctions.CameraGestureListener
import com.outlook.bselzer1.chess.ui.sharedfunctions.GameColor

/**
 * The game screen.
 */
class GameScreen(boardName: BoardName) : GdxGameScreen(OrthographicCamera())
{
    //TODO non-consistent font (ex: invalid event dialog text will be larger in the next game)

    /**
     * The camera gesture listener.
     */
    private val cameraGestureListener: CameraGestureListener = CameraGestureListener(camera)

    /**
     * The game board.
     */
    private val board: Board = boardName.createBoard().apply {
        initializePieces()
        startWithPlayer(topColor)
        resolution = resolution()
    }

    /**
     * The game board actor.
     */
    private val boardActor: BoardActor

    init
    {
        Gdx.graphics.applyContinuousRendering(true)

        //Create after the viewport is applied to use default scaling.
        boardActor = BoardActor.createActor(board)
        boardActor.debug = SettingsScreen.isDebug()
        boardActor.centerOnCamera()
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
     * Display the victor in a dialog.
     */
    private fun resolution(): (PlayerColor) -> Unit
    {
        return { victor ->
            //Do not allow pieces to be moved.
            stage.actors.forEach { actor -> actor.apply { touchable = Touchable.disabled } }

            object : Dialog("Game Ended", game.skinDefault)
            {
                init
                {
                    isMovable = false
                    isModal = false
                }

                override fun draw(batch: Batch?, parentAlpha: Float)
                {
                    centerOnCamera()
                    super.draw(batch, parentAlpha)
                }

                override fun result(`object`: Any?)
                {
                    game.screen = MainMenuScreen()
                }
            }.text("${victor.toDisplayableString()} wins!", defaultLabelStyle())
                    .button("Back", null, defaultTextButtonStyle())
                    .show(stage)
        }
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

        //Rotate
        if (Gdx.input.isKeyPressed(Input.Keys.Q))
        {
            camera.rotate(-.5f, 0f, 0f, 1f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E))
        {
            camera.rotate(.5f, 0f, 0f, 1f)
        }

        //Move
        //Do not adjust the camera based on touch when over the board.
        val cursorPosition = worldCursorPosition()
        if (!boardActor.containsPoint(cursorPosition.x, cursorPosition.y) && Gdx.input.isTouched)
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
    }
}