package com.outlook.bselzer1.chess.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.outlook.bselzer1.chess.GdxGame

object DesktopLauncher
{
    @JvmStatic
    fun main(arg: Array<String>)
    {
        val config = LwjglApplicationConfiguration()
        config.title = "Chess"
        config.resizable = false

        LwjglApplication(GdxGame(), config)
    }
}