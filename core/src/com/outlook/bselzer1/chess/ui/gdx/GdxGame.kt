package com.outlook.bselzer1.chess.ui.gdx

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import com.outlook.bselzer1.chess.ui.screen.main.MainMenuScreen
import com.outlook.bselzer1.chess.ui.screen.settings.SettingsScreen

/**
 * The game.
 */
class GdxGame : Game()
{
    override fun create()
    {
        GdxCompanion.GAME = this
        SettingsScreen.setDisplay(true) //Must be before the screen is created so that the world size is the same as the width/height.
        setScreen(MainMenuScreen())
    }

    override fun setScreen(screen: Screen?)
    {
        getScreen()?.dispose()
        super.setScreen(screen)
    }

    override fun dispose()
    {
        super.dispose()
        GdxCompanion.dispose()
    }
}