package com.outlook.bselzer1.chess.ui.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.game.ai.Difficulty
import com.outlook.bselzer1.chess.game.board.BoardName
import com.outlook.bselzer1.chess.sharedfunctions.extension.*
import com.outlook.bselzer1.chess.ui.actor.dialog.GdxGameDialog
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.gdx.GdxScreen

/**
 * The main menu screen.
 */
class MainMenuScreen : GdxScreen()
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
        super.show()
        setupLayout()
    }

    override fun resize(width: Int, height: Int)
    {
        super.resize(width, height)
        GdxCompanion.STAGE.clear()
        setupLayout()
    }

    /**
     * Add the play button.
     * Add the settings button if applicable.
     * Add the exit button.
     */
    private fun setupLayout()
    {
        val width: Float = buttonWidth()
        val height: Float = buttonHeight()
        val pad: Float = buttonPad()
        val fontSize: Int = buttonFontSize()

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
                        GdxCompanion.GAME.screen = GameScreen(BoardName.WESTERN)
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
                    GdxCompanion.GAME.screen = SettingsScreen()
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

        GdxCompanion.STAGE.addActor(tblRoot)
    }
}