package com.outlook.bselzer1.chess.ui.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.sharedfunctions.extension.*
import com.outlook.bselzer1.chess.ui.sharedfunctions.DisplaySize
import com.outlook.bselzer1.chess.ui.sharedfunctions.DisplayType
import com.outlook.bselzer1.chess.ui.sharedfunctions.GameColor
import com.outlook.bselzer1.chess.ui.sharedfunctions.Resolution
import kotlin.math.roundToInt

//TODO add frame rate???

/**
 * The settings screen.
 */
class SettingsScreen : GdxGameScreen(OrthographicCamera())
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
        private const val DEFAULT_VSYNC = true

        /**
         * The preference name for this screen.
         */
        private const val PREF_NAME = "SETTINGS"

        /**
         * The preference key for storing the screen width.
         */
        private const val KEY_WIDTH = "WIDTH"

        /**
         * The preference key for storing the screen height.
         */
        private const val KEY_HEIGHT = "HEIGHT"

        /**
         * The preference key for storing the screen's display type.
         * Always fullscreen for Android.
         */
        private const val KEY_DISPLAY_TYPE = "DISPLAY_TYPE"

        /**
         * The preference key for storing whether or not VSync should be enabled.
         * Always on for Android.
         */
        private const val KEY_VSYNC = "VERTICAL_SYNCHRONIZATION"

        /**
         * The preference.
         */
        private val pref = Gdx.app.getPreferences(PREF_NAME)

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
        Gdx.input.inputProcessor = stage
        setupLayout()
    }

    /**
     * Render the background and stage.
     *
     * @param delta the time in seconds since the last render.
     */
    override fun render(delta: Float)
    {
        Gdx.gl20.renderBackgroundColor(GameColor.DEFAULT_BACKGROUND)
        stage.act()
        stage.draw()
    }

    /**
     * Resize the viewport and recreate the layout of the stage.
     *
     * @param width  the new width
     * @param height the new height
     */
    override fun resize(width: Int, height: Int)
    {
        viewport.update(width, height, true)
        stage.clear()
        setupLayout()
    }

    override fun pause()
    {
    }

    override fun resume()
    {
    }

    override fun hide()
    {
    }

    override fun dispose()
    {
        stage.dispose()
    }

    /**
     * Add the display section.
     * Add the resolution setting.
     * Add the display type setting.
     * Add the VSync setting.
     */
    private fun setupLayout()
    {
        val width: Float = buttonWidth()
        val height: Float = buttonHeight()
        val padLarge: Float = buttonPad()
        val padMedium = padLarge / 2
        val fontSize: Int = buttonFontSize()

        val btnStyle = defaultTextButtonStyle().apply {
            font = generateFont(fontSize)
        }

        val lblFont: BitmapFont = generateFont((fontSize / 1.5).roundToInt())
        val lblStyle = defaultLabelStyle().apply {
            font = lblFont
        }

        val sbStyle = defaultSelectBoxStyle().apply {
            font = lblFont
            listStyle.font = lblFont
        }

        /**
         * Create the display size select box for selecting the game difficulty.
         */
        fun createDisplaySizeSelection(): SelectBox<Resolution> = SelectBox<Resolution>(sbStyle).apply {
            items = DisplaySize.DEVICE_RESOLUTIONS
            selected = Resolution.CURRENT_RESOLUTION

            addListener(object : ChangeListener()
            {
                override fun changed(event: ChangeEvent, actor: Actor)
                {
                    val sb = actor as SelectBox<*>

                    val resolution = Resolution(sb.selected.toString())
                    val currentResolution: Resolution = Resolution.CURRENT_RESOLUTION
                    if (resolution.compareTo(currentResolution) == 0)
                    {
                        //Don't set the display again if the resolution has not changed.
                        return
                    }

                    pref.putInteger(KEY_WIDTH, resolution.width).putInteger(KEY_HEIGHT, resolution.height).flush()
                    setDisplay(true)
                }
            })
        }

        /**
         * Create the display type select box for selecting how the application window should be displayed.
         */
        fun createDisplayTypeSelection(): SelectBox<DisplayType> = SelectBox<DisplayType>(sbStyle).apply {
            items = DisplayType.DEVICE_DISPLAY_TYPES
            selected = DisplayType.CURRENT_DISPLAY_TYPE

            addListener(object : ChangeListener()
            {
                override fun changed(event: ChangeEvent, actor: Actor)
                {
                    val sb = actor as SelectBox<*>

                    val displayType: DisplayType? = DisplayType.getDisplayType(sb.selected.toString())
                    if (displayType == null || displayType == DisplayType.CURRENT_DISPLAY_TYPE)
                    {
                        return
                    }

                    pref.putString(KEY_DISPLAY_TYPE, displayType.toString()).flush()
                    setDisplay(true)
                }
            })
        }

        /**
         * Create the select box for selecting how VSync is enabled.
         */
        fun createVSyncSelection(): SelectBox<String> = SelectBox<String>(sbStyle).apply {
            setItems(*booleanAsUiString())
            selected = pref.getBoolean(KEY_VSYNC, DEFAULT_VSYNC).toUiString()
            isDisabled = !Gdx.graphics.supportsDisplayModeChange()
            addListener(object : ChangeListener()
            {
                override fun changed(event: ChangeEvent, actor: Actor)
                {
                    val sb = actor as SelectBox<*>
                    pref.putBoolean(KEY_VSYNC, sb.selected.toString().uiToBoolean()).flush()
                    setDisplay(false)
                }
            })
        }

        /**
         * Create the back button for returning to the main screen.
         */
        fun createBackButton(): TextButton = TextButton("Back", btnStyle).apply {
            addListener(object : ChangeListener()
            {
                override fun changed(event: ChangeEvent, actor: Actor)
                {
                    game.screen = MainMenuScreen()
                }
            })
        }

        //Table for all of the settings
        val tblDisplay = Table().apply {
            //Resolution
            add(Label("Resolution:", lblStyle)).padTop(padLarge)
            add(createDisplaySizeSelection()).padLeft(padLarge).padTop(padLarge).fillX()
            row()

            //Display type
            add(Label("Display Type:", lblStyle)).padTop(padMedium)
            add(createDisplayTypeSelection()).padLeft(padLarge).padTop(padMedium).fillX()
            row()

            //VSync
            add(Label("VSync:", lblStyle)).padTop(padMedium)
            add(createVSyncSelection()).padLeft(padLarge).padTop(padMedium).fillX()
            row()
        }

        val tblRoot = Table().apply {
            setFillParent(true)
            top()
            pad(padLarge)

            //Title
            add(Label("Display", LabelStyle(btnStyle.font, Color.WHITE)))
            row()

            //Settings
            add(tblDisplay).top().expand()
            row()

            //Back button
            add(createBackButton()).minWidth(width).minHeight(height).bottom()

            if (isDebug()) debugAll()
        }

        stage.addActor(tblRoot)
    }
}