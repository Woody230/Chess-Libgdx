package com.outlook.bselzer1.chess.ui.gdx.actor

//TODO make GdxActor abstraction class over existing actors
/**
 * Marker interface for Gdx actors.
 */
interface IGdxActor<Style> : IStyle<Style>

/**
 * Modify the associated style and apply the changes to the actor by setting it.
 */
inline fun <Actor : IGdxActor<Style>, Style> Actor.applyStyle(block: Style.() -> Unit): Actor
{
    val style = getStyle().apply(block)

    //Actor styles must call the setter for the changes to be applied.
    setStyle(style)
    return this
}