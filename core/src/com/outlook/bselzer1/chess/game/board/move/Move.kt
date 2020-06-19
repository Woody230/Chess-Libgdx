package com.outlook.bselzer1.chess.game.board.move

import com.outlook.bselzer1.chess.game.piece.Piece

/**
 * [fromPiece] moves from [fromPosition] to [toPosition] to capture [toPiece] if it exists
 */
data class Move(val fromPosition: Position, val toPosition: Position, val fromPiece: Piece<*>, val toPiece: Piece<*>?)
