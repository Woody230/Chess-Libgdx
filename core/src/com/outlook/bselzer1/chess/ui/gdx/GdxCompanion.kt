package com.outlook.bselzer1.chess.ui.gdx

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.Viewport
import com.outlook.bselzer1.chess.ui.actor.piece.PieceActor
import com.outlook.bselzer1.chess.ui.screen.settings.SettingsScreen
import ktx.async.KtxAsync

/**
 * Gdx game companion used for storing commonly used ui related elements in order to avoid passing them throughout the codebase.
 */
object GdxCompanion : Disposable
{
    init
    {
        KtxAsync.initiate()
        Gdx.app.logLevel = if (SettingsScreen.isDebug()) Application.LOG_DEBUG else Application.LOG_NONE
        //Gdx.input.setCatchBackKey(true); //TODO catching back button
    }

    /**
     * The current game.
     */
    lateinit var GAME: GdxGame

    /**
     * The sprite batch.
     */
    val BATCH: SpriteBatch = SpriteBatch()

    /**
     * The default atlas.
     */
    val ATLAS: TextureAtlas = TextureAtlas("default/uiskin.atlas")

    /**
     * The default skin.
     */
    val SKIN: GdxSkin = GdxSkin(Skin(Gdx.files.internal("default/uiskin.json"), ATLAS))

    /**
     * The camera for the current screen.
     */
    var CAMERA = OrthographicCamera()
        private set

    /**
     * The viewport for the camera.
     */
    var VIEWPORT: Viewport = GdxViewport()
        private set

    /**
     * The root for the current screen.
     */
    var STAGE: Stage = Stage(VIEWPORT, BATCH)
        private set

    /**
     * Setup the stage with a new viewport and camera.
     */
    internal fun setupStage(viewport: Viewport = GdxViewport())
    {
        CAMERA = OrthographicCamera()
        VIEWPORT = viewport
        VIEWPORT.camera = CAMERA

        STAGE.dispose()
        STAGE = Stage(VIEWPORT, BATCH)
        SKIN.setup()
    }

    /**
     * Dispose of common resources.
     */
    override fun dispose()
    {
        BATCH.dispose()
        SKIN.dispose()
        ATLAS.dispose()
        STAGE.dispose()
        PieceActor.dispose()
        GdxFontCompanion.dispose()
    }
}

