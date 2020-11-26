package com.outlook.bselzer1.chess.ui.screen.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.BoardName
import com.outlook.bselzer1.chess.sharedfunctions.extension.applyContinuousRendering
import com.outlook.bselzer1.chess.sharedfunctions.extension.centerOn
import com.outlook.bselzer1.chess.sharedfunctions.extension.containsPoint
import com.outlook.bselzer1.chess.sharedfunctions.extension.worldCursorPosition
import com.outlook.bselzer1.chess.ui.actor.board.BoardActor
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.gdx.GdxScreen
import com.outlook.bselzer1.chess.ui.screen.settings.SettingsScreen
import com.outlook.bselzer1.chess.ui.sharedfunctions.CameraGestureListener

/**
 * The game screen.
 */
class GameScreen(boardName: BoardName) : GdxScreen()
{
    /**
     * The camera gesture listener.
     */
    private val cameraGestureListener: CameraGestureListener = CameraGestureListener()

    /**
     * The game board actor.
     */
    private val boardActor: BoardActor

    /**
     * The game board.
     */
    private val board: Board = boardName.createBoard().apply {
        initializePieces()
        startWithPlayer(topColor)
        resolution = { victor ->
            //Do not allow pieces to be moved.
            GdxCompanion.STAGE.actors.forEach { actor -> actor.apply { touchable = Touchable.disabled } }
            VictoryDialog(victor).show()
        }
    }

    init
    {
        Gdx.graphics.applyContinuousRendering(true)

        //Create after the viewport is applied to use default scaling.
        boardActor = BoardActor.createActor(board).apply {
            debug = SettingsScreen.isDebug()
        }
    }

    override fun show()
    {
        super.show()

        val input = InputMultiplexer()
        input.addProcessor(GdxCompanion.STAGE)
        input.addProcessor(GestureDetector(cameraGestureListener))
        Gdx.input.inputProcessor = input

        GdxCompanion.STAGE.addActor(boardActor)
        GdxCompanion.CAMERA.centerOn(boardActor)
    }

    override fun render(delta: Float)
    {
        super.render(delta)
        handleInput()
    }

    override fun resize(width: Int, height: Int)
    {
        super.resize(width, height)
        boardActor.resize(width, height)
        GdxCompanion.CAMERA.centerOn(boardActor)
    }

    /**
     * Handle player input.
     */
    private fun handleInput()
    {
        //TODO separate class
        //TODO key bindings
        //TODO sensitivity

        val camera = GdxCompanion.CAMERA

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

        if (Gdx.input.isButtonJustPressed(Input.Buttons.MIDDLE))
        {
            //Reset camera position
            camera.centerOn(boardActor)
        }
    }
}