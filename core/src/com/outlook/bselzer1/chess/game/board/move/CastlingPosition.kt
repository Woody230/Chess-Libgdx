package com.outlook.bselzer1.chess.game.board.move

import com.outlook.bselzer1.chess.game.piece.Piece

/**
 * A representation of castling where the [king] will move to [newKingPosition] and [rook] moves to [newRookPosition].
 */
data class CastlingPosition(val newKingPosition: Position, val newRookPosition: Position, val king: Piece<*>, val rook: Piece<*>)
