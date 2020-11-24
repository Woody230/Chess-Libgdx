package com.outlook.bselzer1.chess.ui.actor.dialog

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Label
import kotlin.properties.Delegates

/**
 * The dialog for displaying messages for at least [duration] seconds.
 */
open class TimedDialog(title: String = "", message: String = "", private val duration: Long = 2) : GdxGameDialog(title)
{
    init
    {
        text(message)
        touchable = Touchable.disabled
        isMovable = false
        isModal = false
    }

    /**
     * The time the dialog was initially shown.
     */
    private var startTime by Delegates.notNull<Long>()

    /**
     * Sets the title.
     */
    fun setTitle(title: String): TimedDialog
    {
        titleLabel.setText(title)
        return this
    }

    /**
     * Sets the message.
     */
    fun setMessage(message: String): TimedDialog
    {
        (contentTable.getChild(0) as Label).setText(message)
        return this
    }

    override fun show(stage: Stage?): Dialog
    {
        return show(stage, null)
    }

    override fun show(stage: Stage?, action: Action?): Dialog
    {
        startTime = System.currentTimeMillis()
        return super.show(stage, action)
    }

    override fun draw(batch: Batch?, parentAlpha: Float)
    {
        super.draw(batch, parentAlpha)

        val elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000
        if (elapsedSeconds >= duration)
        {
            //Stop showing the dialog after time has elapsed.
            remove()
        }
    }
}