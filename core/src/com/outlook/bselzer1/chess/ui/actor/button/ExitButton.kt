package com.outlook.bselzer1.chess.ui.actor.button

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTextButton

/**
 * The button for closing the application.
 */
class ExitButton : GdxTextButton("Exit")
{
    init
    {
        addListener(object : ChangeListener()
        {
            override fun changed(event: ChangeEvent, actor: Actor)
            {
                Gdx.app.exit()
            }
        })
    }
}