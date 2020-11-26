package com.outlook.bselzer1.chess.ui.screen.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.outlook.bselzer1.chess.sharedfunctions.extension.addTo
import com.outlook.bselzer1.chess.sharedfunctions.extension.applyContinuousRendering
import com.outlook.bselzer1.chess.sharedfunctions.extension.standardPad
import com.outlook.bselzer1.chess.ui.actor.button.ExitButton
import com.outlook.bselzer1.chess.ui.actor.button.SettingsButton
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.gdx.GdxScreen
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTable
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTextButton
import com.outlook.bselzer1.chess.ui.screen.settings.SettingsScreen
import com.outlook.bselzer1.chess.ui.sharedfunctions.UiDirection

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
        val tblRoot = GdxTable().apply {
            debug = SettingsScreen.isDebug()
            setFillParent(true)

            //Play
            PlayButton().applyTo(this)
            row()

            //Settings
            if (Gdx.graphics.supportsDisplayModeChange())
            {
                SettingsButton().applyTo(this)
                row()
            }

            //Exit
            ExitButton().applyTo(this)
            row()
        }

        GdxCompanion.STAGE.addActor(tblRoot)
    }

    /**
     * Standard main menu button initialization.
     */
    private fun GdxTextButton.applyTo(table: Table)
    {
        addTo(table).fillX().standardPad(UiDirection.TOP, UiDirection.BOTTOM)
    }
}