package com.outlook.bselzer1.chess.ui.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.game.ai.Difficulty
import com.outlook.bselzer1.chess.game.board.BoardName
import com.outlook.bselzer1.chess.sharedfunctions.extension.*
import com.outlook.bselzer1.chess.ui.actor.dialog.GdxGameDialog
import com.outlook.bselzer1.chess.ui.sharedfunctions.GameColor

/**
 * The main menu screen.
 */
class MainMenuScreen : GdxGameScreen(OrthographicCamera())
{
    /**
     * Initializes the screen.
     */
    init
    {
        Gdx.graphics.applyContinuousRendering(false)
    }

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

        stage.apply {
            act()
            draw()
        }
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

        val style = defaultTextButtonStyle().apply {
            font = generateFont(fontSize)
        }

        val tblRoot = Table().apply {
            debug = SettingsScreen.isDebug()
            setFillParent(true)
            columnDefaults(0).minSize(width, height).padTop(pad).padBottom(pad)
        }

        /**
         * Create the play button for starting a game.
         */
        fun createPlayButton(): TextButton
        {
            /**
             * Create the difficulty button for selecting the associated difficulty enum.
             */
            fun createDifficultyButton(difficulty: Difficulty): TextButton = TextButton(difficulty.toString(), style).apply {
                addListener(object : ChangeListener()
                {
                    override fun changed(event: ChangeEvent, actor: Actor)
                    {
                        //TODO set difficulty
                        //TODO board selection
                        game.screen = GameScreen(BoardName.WESTERN)
                    }
                })
            }

            /**
             * Create the cancel button for cancelling the difficulty selection.
             */
            fun createCancelButton(dialog: Dialog): TextButton = TextButton("Cancel", style).apply {
                addListener(object : ChangeListener()
                {
                    override fun changed(event: ChangeEvent, actor: Actor)
                    {
                        dialog.hide()
                    }
                })
            }

            return TextButton("Play", style).apply {
                addListener(object : ChangeListener()
                {
                    override fun changed(event: ChangeEvent, actor: Actor)
                    {
                        val dialog = GdxGameDialog("AI Difficulty").apply {
                            isMovable = false
                        }

                        //Add difficulty buttons on first row then center the cancel button on the next row.
                        val tblRootDialog = Table().apply {
                            debug = SettingsScreen.isDebug()
                            Difficulty.values().forEach { difficulty ->
                                add(createDifficultyButton(difficulty)).minSize(width, height).pad(pad)
                            }
                            row()
                            add()
                            add(createCancelButton(dialog)).minSize(width, height).pad(pad)
                        }

                        dialog.add(tblRootDialog)
                        dialog.show(stage)
                    }
                })
            }
        }

        /**
         * Create the settings button for going to the settings screen.
         */
        fun createSettingsButton(): TextButton = TextButton("Settings", style).apply {
            addListener(object : ChangeListener()
            {
                override fun changed(event: ChangeEvent, actor: Actor)
                {
                    game.screen = SettingsScreen()
                }
            })
        }

        /**
         * Create the exit button for closing the application.
         */
        fun createExitButton(): TextButton = TextButton("Exit", style).apply {
            addListener(object : ChangeListener()
            {
                override fun changed(event: ChangeEvent, actor: Actor)
                {
                    Gdx.app.exit()
                }
            })
        }

        tblRoot.apply {
            //Play
            add(createPlayButton())
            row()

            //Settings
            if (Gdx.graphics.supportsDisplayModeChange())
            {
                add(createSettingsButton())
                row()
            }

            //Exit
            add(createExitButton())
            row()
        }

        stage.addActor(tblRoot)
    }
}