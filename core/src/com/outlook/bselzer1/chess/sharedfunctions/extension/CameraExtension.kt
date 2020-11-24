package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * Center the camera on [actor].
 */
fun Camera.centerOn(actor: Actor)
{
    position.x = actor.x + actor.width / 2
    position.y = actor.y + actor.height / 2
}

