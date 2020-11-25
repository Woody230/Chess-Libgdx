package com.outlook.bselzer1.chess.ui.screen.settings

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.outlook.bselzer1.chess.sharedfunctions.extension.*
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.gdx.GdxScreen
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxLabel
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTable
import com.outlook.bselzer1.chess.ui.sharedfunctions.DisplayType
import com.outlook.bselzer1.chess.ui.sharedfunctions.UiDirection

//TODO add frame rate???

/**
 * The settings screen.
 */
class SettingsScreen : GdxScreen()
{
    companion object
    {
        /**
         * Whether or not the application is in debug mode.
         */
        private const val IS_DEBUG = true

        /**
         * Method used to avoid lint warnings.
         * @return whether or not the application is in debug mode
         */
        fun isDebug(): Boolean
        {
            return IS_DEBUG
        }

        /**
         * The default setting for the screen's display type.
         */
        private val DEFAULT_DISPLAY_TYPE: DisplayType = DisplayType.WINDOWED

        /**
         * The default setting for whether or not VSync is enabled.
         */
        internal const val DEFAULT_VSYNC = true

        /**
         * The preference key for storing the screen width.
         */
        internal const val KEY_WIDTH = "WIDTH"

        /**
         * The preference key for storing the screen height.
         */
        internal const val KEY_HEIGHT = "HEIGHT"

        /**
         * The preference key for storing the screen's display type.
         * Always fullscreen for Android.
         */
        internal const val KEY_DISPLAY_TYPE = "DISPLAY_TYPE"

        /**
         * The preference key for storing whether or not VSync should be enabled.
         * Always on for Android.
         */
        internal const val KEY_VSYNC = "VERTICAL_SYNCHRONIZATION"

        /**
         * The preference.
         */
        internal val pref = Gdx.app.getPreferences("SETTINGS")

        /**
         * Sets the display of the window using the width and height stored in preferences.
         * If the display cannot be changed using what is stored in preferences then it is set using the current window's width and height.
         *
         * @param centerWindow whether or not the window should be centered after setting the display
         */
        fun setDisplay(centerWindow: Boolean)
        {
            if (!setDisplay(WIDTH, HEIGHT, centerWindow))
            {
                pref.remove(KEY_DISPLAY_TYPE)
                pref.flush()
                setDisplay(Gdx.graphics.width, Gdx.graphics.height, centerWindow)
            }
        }

        /**
         * Sets the display of the window using `width` and `height` and the remaining settings stored in preferences.
         *
         * @param width        the new width of the window
         * @param height       the new height of the window
         * @param centerWindow whether or not the window should be centered after setting the display
         * @return whether or not the display was able to be set
         */
        private fun setDisplay(width: Int, height: Int, centerWindow: Boolean): Boolean
        {
            val graphics = Gdx.graphics
            if (!graphics.supportsDisplayModeChange()) //Android/iOS don't support changes.
            {
                return false
            }

            val vsync = pref.getBoolean(KEY_VSYNC, DEFAULT_VSYNC)
            graphics.setVSync(vsync)

            val displayMode = graphics.displayMode
            val typeName = pref.getString(KEY_DISPLAY_TYPE, DEFAULT_DISPLAY_TYPE.toString())

            var type: DisplayType? = DisplayType.getDisplayType(typeName)
            if (type == null)
            {
                pref.putString(KEY_DISPLAY_TYPE, DEFAULT_DISPLAY_TYPE.toString()).flush()
                type = DEFAULT_DISPLAY_TYPE
            }

            val able: Boolean = when (type)
            {
                DisplayType.WINDOWED ->
                {
                    graphics.setUndecorated(false)
                    graphics.setWindowedMode(width, height)
                }

                DisplayType.FULLSCREEN, DisplayType.BORDERLESS_FULLSCREEN -> graphics.setFullscreenMode(displayMode)
            }

            if (centerWindow)
            {
                graphics.centerWindow()
            }

            return able
        }

        /**
         * @return the width stored in preferences or the current window's width if it is not found
         */
        val WIDTH: Int
            get() = pref.getInteger(KEY_WIDTH, Gdx.graphics.width)

        /**
         * @return the height stored in preferences or the current window's height if it is not found
         */
        val HEIGHT: Int
            get() = pref.getInteger(KEY_HEIGHT, Gdx.graphics.height)
    }

    /**
     * Initializes the screen.
     */
    init
    {
        Gdx.graphics.applyContinuousRendering(false)
    }

    /**
     * Sets the layout of the stage.
     */
    override fun show()
    {
        super.show()
        setupLayout()
    }

    /**
     * Resize the viewport and recreate the layout of the stage.
     *
     * @param width  the new width
     * @param height the new height
     */
    override fun resize(width: Int, height: Int)
    {
        super.resize(width, height)
        GdxCompanion.STAGE.clear()
        setupLayout()
    }

    /**
     * Add the display section.
     * Add the resolution setting.
     * Add the display type setting.
     * Add the VSync setting.
     */
    private fun setupLayout()
    {
        //Table for all of the settings
        val tblDisplay = Table().apply table@{
            //Resolution
            GdxLabel("Resolution:").apply {
                addTo(this@table).standardPad(UiDirection.TOP).fillX()
                setAlignment(Align.right)
            }
            DisplaySizeSelection().apply {
                addTo(this@table).standardPad(UiDirection.TOP, UiDirection.LEFT).fillX()
            }
            row()

            //Display type
            GdxLabel("Display Type:").apply {
                addTo(this@table).smallPad(UiDirection.TOP).fillX()
                setAlignment(Align.right)
            }
            DisplayTypeSelection().apply {
                addTo(this@table).standardPad(UiDirection.LEFT).smallPad(UiDirection.TOP).fillX()
            }
            row()

            //VSync
            GdxLabel("VSync:").apply {
                addTo(this@table).smallPad(UiDirection.TOP).fillX()
                setAlignment(Align.right)
            }
            VSyncSelection().apply {
                addTo(this@table).standardPad(UiDirection.LEFT).smallPad(UiDirection.TOP).fillX()
            }
            row()
        }

        val tblRoot = GdxTable().apply table@{
            setFillParent(true)
            top()
            standardPad()

            //Title
            GdxLabel("Display").apply {
                style = GdxCompanion.SKIN.getLabelStyle().apply {
                    fontColor = Color.WHITE
                }
                addTo(this@table)
            }
            row()

            //Settings
            add(tblDisplay).top().expand()
            row()

            //Back button
            BackButton().apply {
                addTo(this@table).standardMinSize().bottom()
            }

            if (isDebug()) debugAll()
        }

        GdxCompanion.STAGE.addActor(tblRoot)
    }
}