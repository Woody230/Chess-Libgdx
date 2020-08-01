package com.outlook.bselzer1.chess.ui.actor.board

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.badlogic.gdx.math.Vector2
import com.outlook.bselzer1.chess.game.board.extend.WesternBoard
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.ui.sharedfunctions.GameColor
import kotlin.math.min

/**
 * The [WesternBoard] ui
 */
class WesternActor(westernBoard: WesternBoard, camera: OrthographicCamera) : BoardActor(westernBoard, camera)
{
    /**
     * The tiled map.
     */
    private val tiledMap: TiledMap = TiledMap()

    /**
     * The tiled map renderer.
     */
    private val renderer = OrthogonalTiledMapRenderer(tiledMap)

    /**
     * The length of the cell.
     */
    private var cellLength: Int = 0

    init
    {
        setLayers(camera.viewportWidth.toInt(), camera.viewportHeight.toInt())
    }

    override fun draw(batch: Batch, parentAlpha: Float)
    {
        //Render the tiled map.
        //Must end the batch and restart it when using a renderer. https://github.com/libgdx/libgdx/wiki/Scene2d#drawing
        batch.end()
        renderer.setView(camera)
        renderer.render()
        batch.begin()

        //Render the pieces on top.
        super.draw(batch, parentAlpha)
    }

    override fun getPieceActorUIPosition(position: Position): Vector2
    {
        return Vector2((cellLength * position.x).toFloat(), (cellLength * position.y).toFloat())
    }

    override fun dispose()
    {
        tiledMap.dispose()
        renderer.dispose()
    }

    /**
     * Set the [tiledMap] layers with scaling based on [width] and [height]
     */
    private fun setLayers(width: Int, height: Int)
    {
        tiledMap.layers.removeAll { true }

        //Create the length to fit based on the smaller side.
        val minSide = min(width, height)
        cellLength = minSide / if (minSide == width) board.size.columnCount else board.size.rowCount

        //Match the piece actor size to the new cell size.
        pieceActors.forEach { actor -> actor.setSize(cellLength.toFloat(), cellLength.toFloat()) }

        //Create a pixmap to alternate colors.
        val pixmap = Pixmap(cellLength * 2, cellLength, Pixmap.Format.RGBA8888)
        pixmap.setColor(GameColor.WESTERN_TILE_1.color)
        pixmap.fillRectangle(0, 0, cellLength, cellLength)
        pixmap.setColor(GameColor.WESTERN_TILE_2.color)
        pixmap.fillRectangle(cellLength, 0, cellLength, cellLength)

        val texture = Texture(pixmap)
        val leftRegion = TextureRegion(texture, 0, 0, cellLength, cellLength)
        val rightRegion = TextureRegion(texture, cellLength, 0, cellLength, cellLength)

        //Create the tiles.
        val layer = TiledMapTileLayer(board.size.columnCount, board.size.rowCount, cellLength, cellLength)
        for (x in 0 until board.size.columnCount)
        {
            for (y in 0 until board.size.rowCount)
            {
                val cell = TiledMapTileLayer.Cell()
                cell.tile = if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) StaticTiledMapTile(leftRegion) else StaticTiledMapTile(rightRegion)
                layer.setCell(x, y, cell)
            }
        }

        tiledMap.layers.add(layer)
    }

    override fun setWidth(width: Float)
    {
        super.setWidth(width)
        setLayers(width.toInt(), height.toInt())
    }

    override fun setHeight(height: Float)
    {
        super.setHeight(height)
        setLayers(width.toInt(), height.toInt())
    }
}