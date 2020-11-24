package com.outlook.bselzer1.chess.ui

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.outlook.bselzer1.chess.ui.actor.PieceActor
import com.outlook.bselzer1.chess.ui.screen.MainMenuScreen
import com.outlook.bselzer1.chess.ui.screen.SettingsScreen
import ktx.async.KtxAsync

/**
 * The game.
 */
class GdxGame : Game()
{
    companion object
    {
        /**
         * This object.
         */
        lateinit var GAME: GdxGame

        init
        {
            KtxAsync.initiate()
        }
    }

    /**
     * The camera.
     */
    lateinit var camera: OrthographicCamera

    /**
     * The sprite batch.
     */
    lateinit var batch: SpriteBatch
        private set

    /**
     * The default atlas.
     */
    lateinit var atlasDefault: TextureAtlas
        private set


    /**
     * The default skin.
     */
    lateinit var skinDefault: Skin
        private set

    override fun create()
    {
        GAME = this

        Gdx.app.logLevel = if (SettingsScreen.isDebug()) Application.LOG_DEBUG else Application.LOG_NONE
        batch = SpriteBatch()
        atlasDefault = TextureAtlas("default/uiskin.atlas")
        skinDefault = Skin(Gdx.files.internal("default/uiskin.json"), atlasDefault)

        //Gdx.input.setCatchBackKey(true); //TODO catching back button

        SettingsScreen.setDisplay(true) //Must be before the screen is created so that the world size is the same as the width/height.
        setScreen(MainMenuScreen())
    }

    override fun setScreen(screen: Screen?)
    {
        getScreen()?.dispose()
        super.setScreen(screen)
    }

    override fun dispose()
    {
        super.dispose()
        batch.dispose()
        skinDefault.dispose()
        atlasDefault.dispose()
        PieceActor.dispose()
    }
}