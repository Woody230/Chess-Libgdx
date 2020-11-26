package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.gdx.GdxFontCompanion.generateFont
import com.outlook.bselzer1.chess.ui.sharedfunctions.BackgroundAppearance

//TODO resizable title

open class GdxDialog(title: String) : Dialog(title, GdxCompanion.SKIN.windowStyle), IGdxActor<Window.WindowStyle>
{
    /**
     * Show the dialog using the companion stage.
     */
    fun show(): GdxDialog
    {
        show(GdxCompanion.STAGE)
        return this
    }

    final override fun text(text: String?): Dialog
    {
        return super.text(GdxLabel(text ?: ""))
    }

    final override fun button(text: String?, `object`: Any?): Dialog
    {
        val button = GdxTextButton(text ?: "").applyStyle {
            font = GdxTextButton.SMALL_FONT_SIZE.generateFont()
        }.apply {
            appearance = BackgroundAppearance.EXACT
        }

        return super.button(button, `object`)
    }
}