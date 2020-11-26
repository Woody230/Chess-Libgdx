package com.outlook.bselzer1.chess

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.outlook.bselzer1.chess.ui.gdx.GdxGame

class AndroidLauncher : AndroidApplication()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        config.useAccelerometer = false
        config.useCompass = false

        initialize(GdxGame(), config)
    }
}