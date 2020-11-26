package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.sharedfunctions.BackgroundAppearance

//TODO resizable title

open class GdxDialog(title: String) : Dialog(title, GdxCompanion.SKIN.windowStyle)
{
    /**
     * Show the dialog using the companion stage.
     */
    fun show(): GdxDialog
    {
        show(GdxCompanion.STAGE)
        return this
    }

    override fun text(text: String?): Dialog
    {
        return super.text(GdxLabel(text ?: ""))
    }

    override fun button(text: String?, `object`: Any?): Dialog
    {
        val button = GdxTextButton(text ?: "").apply {
            appearance = BackgroundAppearance.EXACT
        }

        return super.button(button, `object`)
    }
}