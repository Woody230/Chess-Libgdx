package com.outlook.bselzer1.chess.ui.sharedfunctions

import com.badlogic.gdx.graphics.Color

/**
 * The background color of value [color].
 */
enum class GameColor(var color: Color)
{
    /**
     * Dark blue.
     */
    DEFAULT_BACKGROUND(Color(.1f, .12f, .16f, 1f)),

    /**
     * Cream.
     */
    WESTERN_TILE_1(Color(1f, 0.8314f, 0.4353f, 1f)),

    /**
     * Brown.
     */
    WESTERN_TILE_2(Color(0.8f, 0.4f, 0f, 1f))
}