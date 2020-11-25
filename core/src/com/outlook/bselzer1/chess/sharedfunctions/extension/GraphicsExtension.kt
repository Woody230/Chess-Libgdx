package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Graphics
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics
import com.badlogic.gdx.math.Vector3
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import org.lwjgl.glfw.GLFW

/**
 * Set whether or not continuous rendering should be used and then attempt to request rendering.
 */
fun Graphics.applyContinuousRendering(continuous: Boolean)
{
    this.isContinuousRendering = continuous
    this.requestRendering()
}

/**
 * Centers the window if the application supports it.
 */
fun Graphics.centerWindow()
{
    if (this is Lwjgl3Graphics)
    {
        val windowCenterX = this.width / 2
        val windowCenterY = this.height / 2
        val displayMode = this.displayMode
        val displayCenterX = displayMode.width / 2
        val displayCenterY = displayMode.height / 2
        this.window.setPosition(displayCenterX - windowCenterX, displayCenterY - windowCenterY)
    }
}

/**
 * @return whether or not the window is undecorated
 */
fun Graphics.isUndecorated(): Boolean
{
    if (this !is Lwjgl3Graphics)
    {
        return false
    }

    val window = this.window.windowHandle
    return GLFW.glfwGetWindowAttrib(window, GLFW.GLFW_DECORATED) == GLFW.GLFW_FALSE
}

/**
 * The position of the cursor within the world through the lens of a camera.
 */
fun worldCursorPosition(): Vector3
{
    return GdxCompanion.CAMERA.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
}
