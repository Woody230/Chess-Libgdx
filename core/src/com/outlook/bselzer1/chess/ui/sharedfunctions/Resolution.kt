package com.outlook.bselzer1.chess.ui.sharedfunctions

import com.outlook.bselzer1.chess.ui.screen.settings.SettingsScreen

/**
 * The resolution.
 * @property width the window width
 * @property height the window height
 */
class Resolution(val width: Int, val height: Int) : Comparable<Resolution>
{
    /**
     * A string representation of the width and height.
     */
    private val info: String

    /**
     * Initializes the resolution.
     *
     * @param info the string representation of the width and height
     */
    constructor(info: String) : this(extractWidth(info), extractHeight(info))

    /**
     * Initializes the resolution.
     */
    init
    {
        info = getResolutionInfo(width, height)
    }

    /**
     * Width takes priority over height when comparing the resolution.
     *
     * @param other the resolution
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
     */
    override fun compareTo(other: Resolution): Int
    {
        return if (width == other.width)
        {
            height.compareTo(other.height)
        }
        else
        {
            width.compareTo(other.width)
        }
    }

    /**
     * The resolution fits when the width and height are both respectively equal to or smaller than the window's width and height.
     *
     * @param resolution the resolution
     * @return whether or not the resolution fits the window
     */
    fun fits(resolution: Resolution): Boolean
    {
        return width <= resolution.width && height <= resolution.height
    }

    override fun hashCode(): Int
    {
        var result = width
        result = 31 * result + height
        return result
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Resolution
        return if (width != that.width) false else height == that.height
    }

    override fun toString(): String
    {
        return info
    }

    companion object
    {
        /**
         * Creates the string "`width` x `height`"
         *
         * @param width  the width
         * @param height the height
         * @return the string representation of the width and height
         */
        private fun getResolutionInfo(width: Int, height: Int): String
        {
            return "$width x $height"
        }

        /**
         * @param info the string representation of the width and height
         * @return the extracted width
         */
        private fun extractWidth(info: String): Int
        {
            val width = info.split("x".toRegex()).toTypedArray()[0]
            return width.replace(" ", "").toInt()
        }

        /**
         * @param info the string representation of the width and height
         * @return the extracted height
         */
        private fun extractHeight(info: String): Int
        {
            val height = info.split("x".toRegex()).toTypedArray()[1]
            return height.replace(" ", "").toInt()
        }

        /**
         * @return the current resolution using the width and height from the settings
         */
        val CURRENT_RESOLUTION: Resolution
            get()
            {
                val width: Int = SettingsScreen.WIDTH
                val height: Int = SettingsScreen.HEIGHT
                return Resolution(width, height)
            }
    }
}