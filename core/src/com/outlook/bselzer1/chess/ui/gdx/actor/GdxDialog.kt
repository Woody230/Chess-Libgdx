package com.outlook.bselzer1.chess.ui.gdx.actor

import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.outlook.bselzer1.chess.sharedfunctions.extension.getLabelStyle
import com.outlook.bselzer1.chess.sharedfunctions.extension.getTextButtonStyle
import com.outlook.bselzer1.chess.sharedfunctions.extension.getWindowStyle
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion

//TODO resizable title

/**
 * Dialog to keep styles from overwriting each other when using the default skin.
 */
open class GdxDialog(title: String) : Dialog(title, GdxCompanion.SKIN)
{
    init
    {
        style = skin.getWindowStyle()
    }

    final override fun text(text: String?): Dialog
    {
        return super.text(text, skin.getLabelStyle())
    }

    final override fun button(text: String?, `object`: Any?): Dialog
    {
        return super.button(text, `object`, skin.getTextButtonStyle())
    }

    /**
     * Show the dialog using the companion stage.
     */
    fun show(): GdxDialog
    {
        show(GdxCompanion.STAGE)
        return this
    }
}