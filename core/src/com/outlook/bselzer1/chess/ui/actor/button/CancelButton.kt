package com.outlook.bselzer1.chess.ui.actor.button

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxDialog
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTextButton

/**
 * The button for hiding a dialog.
 */
class CancelButton(dialog: GdxDialog) : GdxTextButton("Cancel")
{
    init
    {
        addListener(object : ChangeListener()
        {
            override fun changed(event: ChangeEvent, actor: Actor)
            {
                dialog.hide()
            }
        })
    }
}