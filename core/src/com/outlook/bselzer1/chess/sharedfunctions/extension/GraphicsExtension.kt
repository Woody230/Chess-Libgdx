package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Graphics
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.math.MathUtils
import com.outlook.bselzer1.chess.ui.sharedfunctions.GameColor
import org.lwjgl.glfw.GLFW

/**
 * Clear using [color]
 */
fun GL20.glClearColor(color: Color)
{
    this.glClearColor(color.r, color.g, color.b, color.a)
}

/**
 * Clear using the value of [gameColor]
 */
fun GL20.glClearColor(gameColor: GameColor)
{
    this.glClearColor(gameColor.color)
}

/**
 * Render the [gameColor] by clearing
 */
fun GL20.renderBackgroundColor(gameColor: GameColor)
{
    this.glClearColor(gameColor)
    this.glClear(GL20.GL_COLOR_BUFFER_BIT)
}

/**
 * Set whether or not continuous rendering should be used and then attempt to request rendering.
 */
fun Graphics.applyContinuousRendering(continuous: Boolean)
{
    this.isContinuousRendering = continuous
    this.requestRendering()
}

/**
 * If the application is WebGL generate a BitmapFont.
 * Otherwise, generates a FreeTypeFont (not compatible with WebGL).
 *
 * @param size size of the font in pixels
 * @return a font
 */
fun generateFont(size: Int): BitmapFont
{
    val font: BitmapFont
    if (Gdx.app.type == Application.ApplicationType.WebGL)
    {
        font = BitmapFont(Gdx.files.internal("default/default.fnt"))
        return font
    }

    val generator = FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"))
    val parameter = FreeTypeFontParameter()
    parameter.size = size

    /* For when you are using FitViewport instead of ScreenViewport.
        parameter.minFilter = Texture.TextureFilter.MipMapLinearLinear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.genMipMaps = true;
    */

    font = generator.generateFont(parameter)
    /* For when you are using FitViewport instead of ScreenViewport.
        font.setUseIntegerPositions(false);
     */

    generator.dispose()
    return font
}

/**
 * @param camera the camera
 * @return the button width
 */
fun buttonWidth(camera: Camera): Float
{
    return camera.viewportWidth / 7
}

/**
 * @param camera the camera
 * @return the button height
 */
fun buttonHeight(camera: Camera): Float
{
    return camera.viewportHeight / 10
}

/**
 * @param camera the camera
 * @return the button padding
 */
fun buttonPad(camera: Camera): Float
{
    return camera.viewportHeight / 25
}

/**
 * @param camera the camera
 * @return the button font size
 */
fun buttonFontSize(camera: Camera): Int
{
    val height = buttonHeight(camera)
    return MathUtils.floor(height / 2)
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
 * Fill the pixmap, ignoring any transparent pixels.
 */
fun Pixmap.replacementFill()
{
    for (x in 0 until this.width)
    {
        for (y in 0 until this.height)
        {
            val pixel = this.getPixel(x, y)

            //Ignore transparent
            if (Color.alpha(pixel.toFloat()) == 0)
            {
                continue
            }

            this.fillRectangle(x, y, 1, 1)
        }
    }
}