package com.outlook.bselzer1.chess.ui.screen

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.outlook.bselzer1.chess.game.ai.Difficulty
import com.outlook.bselzer1.chess.game.board.BoardName
import com.outlook.bselzer1.chess.sharedfunctions.extension.*
import com.outlook.bselzer1.chess.ui.GdxGame
import com.outlook.bselzer1.chess.ui.sharedfunctions.GameColor

/**
 * The main menu screen.
 */
class MainMenuScreen : Screen
{
    /**
     * The game.
     */
    private val game: GdxGame = GdxGame.GAME

    /**
     * The camera.
     */
    private val camera: OrthographicCamera = game.camera

    /**
     * The viewport for the camera.
     */
    private val viewport: Viewport

    /**
     * The root of the screen.
     */
    private val stage: Stage

    /**
     * Set the layout of the stage.
     */
    override fun show()
    {
        Gdx.input.inputProcessor = stage
        setupLayout()
    }

    /**
     * Render the background and stage.
     *
     * @param delta the time in seconds since the last render.
     */
    override fun render(delta: Float)
    {
        Gdx.gl20.renderBackgroundColor(GameColor.DEFAULT_BACKGROUND)
        stage.act()
        stage.draw()
    }

    /**
     * Resize the viewport and recreate the layout of the stage.
     *
     * @param width  the new width
     * @param height the new height
     */
    override fun resize(width: Int, height: Int)
    {
        viewport.update(width, height, true)
        stage.clear()
        setupLayout()
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
        stage.dispose()
    }

    /**
     * Add the play button.
     * Add the settings button if applicable.
     * Add the exit button if applicable.
     */
    private fun setupLayout()
    {
        val width: Float = buttonWidth(camera)
        val height: Float = buttonHeight(camera)
        val pad: Float = buttonPad(camera)
        val fontSize: Int = buttonFontSize(camera)

        val skin = game.skinDefault
        val font: BitmapFont = generateFont(fontSize)
        val style = skin!!.get(TextButtonStyle::class.java)
        style.font = font

        val tblRoot = Table()
        tblRoot.debug = SettingsScreen.isDebug()
        tblRoot.setFillParent(true)
        tblRoot.columnDefaults(0).minSize(width, height).padTop(pad).padBottom(pad)

        val btnPlay = TextButton("Play", style)
        btnPlay.addListener(object : ChangeListener()
        {
            override fun changed(event: ChangeEvent, actor: Actor)
            {
                val dialog = Dialog("AI Difficulty", skin)
                dialog.isMovable = false

                val tblRootDialog = Table()
                tblRootDialog.debug = SettingsScreen.isDebug()

                var btn: TextButton
                val difficulties: Array<Difficulty> = Difficulty.values()
                for (difficulty in difficulties)
                {
                    btn = TextButton(difficulty.toString(), style)
                    btn.addListener(object : ChangeListener()
                    {
                        override fun changed(event: ChangeEvent, actor: Actor)
                        {
                            //TODO set difficulty
                            //TODO board selection
                            game.screen = GameScreen(BoardName.WESTERN)
                        }
                    })
                    tblRootDialog.add(btn).minSize(width, height).pad(pad)
                }
                tblRootDialog.row()
                tblRootDialog.add()

                btn = TextButton("Cancel", style)
                btn.addListener(object : ChangeListener()
                {
                    override fun changed(event: ChangeEvent, actor: Actor)
                    {
                        dialog.hide()
                    }
                })
                tblRootDialog.add(btn).minSize(width, height).pad(pad)
                dialog.add(tblRootDialog)

                dialog.show(stage)
            }
        })
        tblRoot.add(btnPlay)
        tblRoot.row()

        if (Gdx.graphics.supportsDisplayModeChange())
        {
            val btnSettings = TextButton("Settings", style)
            btnSettings.addListener(object : ChangeListener()
            {
                override fun changed(event: ChangeEvent, actor: Actor)
                {
                    game.screen = SettingsScreen()
                }
            })
            tblRoot.add(btnSettings)
            tblRoot.row()
        }

        //Exiting is against iOS guidelines.
        if (Gdx.app.type != Application.ApplicationType.iOS)
        {
            val btnExit = TextButton("Exit", style)
            btnExit.addListener(object : ChangeListener()
            {
                override fun changed(event: ChangeEvent, actor: Actor)
                {
                    Gdx.app.exit()
                }
            })
            tblRoot.add(btnExit)
            tblRoot.row()
        }

        stage.addActor(tblRoot)
    }

    /**
     * Initializes the screen.
     */
    init
    {
        Gdx.graphics.applyContinuousRendering(false)
        viewport = ScreenViewport(camera)
        stage = Stage(viewport, game.batch)
    }
}