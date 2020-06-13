package com.outlook.bselzer1.chess.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.outlook.bselzer1.chess.GdxGame

object DesktopLauncher
{
    @JvmStatic
    fun main(arg: Array<String>)
    {
        val config = Lwjgl3ApplicationConfiguration()
        config.setTitle("Chess")
        config.setResizable(false)

        Lwjgl3Application(GdxGame(), config)
    }
}