package com.outlook.bselzer1.chess.game.board.move

import com.badlogic.gdx.math.Vector2
import com.outlook.bselzer1.chess.game.piece.Piece

/**
 * [fromPiece] moves from [fromPosition] to [toPosition] to capture [toPiece] if it exists
 */
data class Move(val fromPosition: Vector2, val toPosition: Vector2, val fromPiece: Piece, val toPiece: Piece?)
