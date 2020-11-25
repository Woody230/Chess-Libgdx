package com.outlook.bselzer1.chess.ui.sharedfunctions

import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion

/**
 * Standard components for ui elements.
 */
open class UiStandard
{
    /**
     * Get the standard width.
     */
    open fun getStandardWidth(): Float
    {
        return 0f
    }

    /**
     * Get the standard height.
     */
    open fun getStandardHeight(): Float
    {
        return 0f
    }

    /**
     * Get the standard padding.
     */
    open fun getStandardPadding(): Float
    {
        return GdxCompanion.CAMERA.viewportHeight / 25
    }

    /**
     * Get the small padding.
     */
    open fun getSmallPadding(): Float
    {
        return getStandardPadding() / 2
    }

    /**
     * Get the standard font size.
     */
    open fun getStandardFontSize(): Int
    {
        throw NotImplementedError("Need to implement standard font size.")
    }
}