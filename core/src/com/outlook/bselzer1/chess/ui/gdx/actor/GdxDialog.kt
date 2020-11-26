package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.gdx.GdxFontCompanion

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
        val label = GdxLabel(text ?: "").apply {
            style.font = GdxFontCompanion.generateFont(GdxLabel.STANDARD_FONT_SIZE / 2)
        }

        return super.text(label)
    }

    override fun button(text: String?, `object`: Any?): Dialog
    {
        return super.button(GdxTextButton(text ?: ""), `object`)
    }
}