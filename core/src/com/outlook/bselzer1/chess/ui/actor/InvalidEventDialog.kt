package com.outlook.bselzer1.chess.ui.actor

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.outlook.bselzer1.chess.ui.GdxGame
import kotlin.properties.Delegates

/**
 * The standard dialog for displaying invalid event messages for at least [duration] seconds.
 */
class InvalidEventDialog(title: String = "", message: String = "", private val duration: Long = 2) : Dialog(title, GdxGame.GAME.skinDefault)
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
    fun setTitle(title: String): InvalidEventDialog
    {
        titleLabel.setText(title)
        return this
    }

    /**
     * Sets the message.
     */
    fun setMessage(message: String): InvalidEventDialog
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
        //Put the dialog near the top of the screen, centered horizontally
        val camera = GdxGame.GAME.camera
        val vector = camera.position
        setPosition(vector.x - width / 2, vector.y + camera.viewportHeight / 2 - .1f * camera.viewportHeight)

        super.draw(batch, parentAlpha)

        val elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000
        if (elapsedSeconds >= duration)
        {
            //Stop showing the dialog after time has elapsed.
            remove()
        }
    }
}