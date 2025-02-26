package com.outlook.bselzer1.chess

import com.badlogic.gdx.backends.iosrobovm.IOSApplication
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration
import com.outlook.bselzer1.chess.ui.gdx.GdxGame
import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit.UIApplication

class IOSLauncher : IOSApplication.Delegate()
{
    override fun createApplication(): IOSApplication
    {
        val config = IOSApplicationConfiguration()
        return IOSApplication(GdxGame(), config)
    }

    companion object
    {
        @JvmStatic
        fun main(argv: Array<String>)
        {
            val pool = NSAutoreleasePool()
            UIApplication.main<UIApplication, IOSLauncher>(argv, null, IOSLauncher::class.java)
            pool.close()
        }
    }
}