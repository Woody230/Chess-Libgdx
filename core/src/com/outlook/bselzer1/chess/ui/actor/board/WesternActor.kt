package com.outlook.bselzer1.chess.ui.actor.board

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.outlook.bselzer1.chess.game.board.extend.WesternBoard
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.ui.sharedfunctions.GameColor
import com.outlook.bselzer3.libgdxlogger.LibgdxLogger
import kotlin.math.min

/**
 * The [WesternBoard] ui
 */
class WesternActor(westernBoard: WesternBoard, camera: OrthographicCamera) : BoardActor(westernBoard, camera)
{
    /**
     * The width of the cell.
     */
    private var cellWidth: Int = 0

    /**
     * The height of the cell.
     */
    private var cellHeight: Int = 0

    /**
     * The sprite for the first colored cell.
     */
    private lateinit var sprite1: Sprite

    /**
     * The sprite for the second colored cell.
     */
    private lateinit var sprite2: Sprite

    init
    {
        //Create the length to fit based on the smaller side.
        val minSide = min(camera.viewportWidth, camera.viewportHeight)
        setSize(minSide, minSide)
    }

    override fun draw(batch: Batch, parentAlpha: Float)
    {
        //Alternate the cell colors.
        (0 until board.size.columnCount).forEach { column ->
            (0 until board.size.rowCount).forEach { row ->
                val sprite = if (row % 2 == 0 && column % 2 == 0 || row % 2 == 1 && column % 2 == 1) sprite1 else sprite2
                val vector = getAbsoluteUiPosition(Position(column, row))
                sprite.setPosition(vector.x, vector.y)
                sprite.draw(batch)
            }
        }

        //Render the pieces on top.
        super.draw(batch, parentAlpha)
    }

    override fun getPieceActorUiPosition(position: Position): Vector2
    {
        return getAbsoluteUiPosition(position)
    }

    override fun getPiecePosition(x: Float, y: Float): Position
    {
        val xPosition = ((x - this.x) / cellWidth).toInt()
        val yPosition = ((y - this.y) / cellHeight).toInt()
        return Position(xPosition, yPosition)
    }

    /**
     * Gets the absolute position within the bounds of the world.
     */
    private fun getAbsoluteUiPosition(position: Position): Vector2
    {
        //Add the position of this actor to get the absolute position.
        val relativePosition = getRelativeUiPosition(position)
        relativePosition.add(x, y)
        return relativePosition
    }

    /**
     * Get the relative position within the bounds of the actor.
     */
    private fun getRelativeUiPosition(position: Position): Vector2
    {
        return Vector2((cellWidth * position.x).toFloat(), (cellHeight * position.y).toFloat())
    }

    /**
     * Set the sprites used in creating an alternating colored board.
     */
    private fun generateSprites()
    {
        //Match the piece actor size to the cell size.
        pieceActors.forEach { actor -> actor.setSize(cellWidth.toFloat(), cellHeight.toFloat()) }

        //Create a pixmap for the two distinctly colored cells.
        val pixmap = Pixmap(cellWidth * 2, cellHeight, Pixmap.Format.RGBA8888)
        pixmap.setColor(GameColor.WESTERN_TILE_1.color)
        pixmap.fillRectangle(0, 0, cellWidth, cellHeight)
        pixmap.setColor(GameColor.WESTERN_TILE_2.color)
        pixmap.fillRectangle(cellWidth, 0, cellWidth, cellHeight)

        val texture = Texture(pixmap)
        sprite1 = Sprite(TextureRegion(texture, 0, 0, cellWidth, cellHeight))
        sprite2 = Sprite(TextureRegion(texture, cellWidth, 0, cellWidth, cellHeight))
        pixmap.dispose()
    }

    /**
     * Setup the pieces on the board to the new size.
     */
    override fun sizeChanged()
    {
        cellWidth = (width / board.size.columnCount).toInt()
        cellHeight = (height / board.size.rowCount).toInt()
        LibgdxLogger.debug("Creating a board of size $width x $height with a cell size of $cellWidth x $cellHeight.")

        generateSprites()
    }
}