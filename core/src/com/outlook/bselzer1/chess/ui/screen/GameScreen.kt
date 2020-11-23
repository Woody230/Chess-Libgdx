package com.outlook.bselzer1.chess.ui.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.BoardName
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.sharedfunctions.extension.*
import com.outlook.bselzer1.chess.ui.GdxGame
import com.outlook.bselzer1.chess.ui.actor.board.BoardActor
import com.outlook.bselzer1.chess.ui.sharedfunctions.CameraGestureListener
import com.outlook.bselzer1.chess.ui.sharedfunctions.GameColor

/**
 * The game screen.
 */
class GameScreen(boardName: BoardName) : Screen
{
    //TODO center camera on the board

    /**
     * The game.
     */
    private val game: GdxGame = GdxGame.GAME

    /**
     * The camera.
     */
    private val camera: OrthographicCamera = game.camera

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
        viewport = ScreenViewport(camera)
        stage = Stage(viewport, game.batch)

        //Create after the viewport is applied to use default scaling.
        boardActor = BoardActor.createActor(board, camera)
        boardActor.debug = SettingsScreen.isDebug()
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

            val skin = game.skinDefault!!
            val font = generateFont(buttonFontSize(camera))

            val btnStyle = skin.get(TextButton.TextButtonStyle::class.java)
            btnStyle.font = font

            val lblStyle = skin.get(Label.LabelStyle::class.java)
            lblStyle.font = font

            object : Dialog("Game Ended", skin)
            {
                init
                {
                    isMovable = false
                    isModal = false
                }

                override fun draw(batch: Batch?, parentAlpha: Float)
                {
                    //Center dialog on screen.
                    setPosition(camera.position.x - (width / 2), camera.position.y - (height / 2))
                    super.draw(batch, parentAlpha)
                }

                override fun result(`object`: Any?)
                {
                    game.screen = MainMenuScreen()
                }
            }.text("${victor.toDisplayableString()} wins!")
                    .button("Back", null, btnStyle)
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