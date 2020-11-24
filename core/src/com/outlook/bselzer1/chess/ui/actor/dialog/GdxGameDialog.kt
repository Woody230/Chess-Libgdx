package com.outlook.bselzer1.chess.ui.actor.dialog

import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.outlook.bselzer1.chess.sharedfunctions.extension.getLabelStyle
import com.outlook.bselzer1.chess.sharedfunctions.extension.getTextButtonStyle
import com.outlook.bselzer1.chess.sharedfunctions.extension.getWindowStyle
import com.outlook.bselzer1.chess.ui.GdxGame

/**
 * Dialog to keep styles from overwriting each other when using the default skin.
 */
open class GdxGameDialog(title: String, skin: Skin = GdxGame.GAME.skinDefault) : Dialog(title, skin)
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
}