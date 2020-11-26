package com.outlook.bselzer1.chess.game.board.move

/**
 * The direction of movement.
 */
enum class Direction(val xIncrement: Int, val yIncrement: Int)
{
    //Standard
    LEFT1_UP1(-1, 1),
    UP1(0, 1),
    RIGHT1_UP1(1, 1),
    RIGHT1(1, 0),
    RIGHT1_DOWN1(1, -1),
    DOWN1(0, -1),
    LEFT1_DOWN1(-1, -1),
    LEFT1(-1, 0),

    //Irregular
    LEFT2_UP1(-2, 1),
    LEFT1_UP2(-1, 2),
    RIGHT1_UP2(1, 2),
    RIGHT2_UP1(2, 1),
    RIGHT2_DOWN1(2, -1),
    RIGHT1_DOWN2(1, -2),
    LEFT1_DOWN2(-1, -2),
    LEFT2_DOWN1(-2, -1)
}