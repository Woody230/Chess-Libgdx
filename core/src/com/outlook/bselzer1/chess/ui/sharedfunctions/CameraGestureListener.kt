package com.outlook.bselzer1.chess.ui.sharedfunctions

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

/**
 * Gesture listener used to move the camera.
 */
class CameraGestureListener(private val camera: OrthographicCamera) : GestureListener
{
    /**
     * The minimum zoom.
     */
    var minZoom = 0.1f

    /**
     * The maximum zoom.
     */
    var maxZoom = 3f

    /**
     * The amount the zoom should change.
     */
    var zoomAmount = 0.02f

    /**
     * The previous delta of distance.
     */
    private var previousDelta = 0f

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean
    {
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean
    {
        return false
    }

    override fun longPress(x: Float, y: Float): Boolean
    {
        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean
    {
        return false
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean
    {
        return false
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean
    {
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean
    {
        //TODO smoother zoom
        //TODO sensitivity
        val delta = initialDistance - distance
        if (MathUtils.isZero(previousDelta - delta, 1f))
        {
            previousDelta = delta
            return false
        }

        camera.zoom += if (delta < 0) -zoomAmount else zoomAmount
        camera.zoom = MathUtils.clamp(camera.zoom, minZoom, maxZoom)
        camera.update()
        previousDelta = delta
        return true
    }

    override fun pinch(initialPointer1: Vector2, initialPointer2: Vector2, pointer1: Vector2, pointer2: Vector2): Boolean
    {
        return false
    }

    override fun pinchStop()
    {
    }
}