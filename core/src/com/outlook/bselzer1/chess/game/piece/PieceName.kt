package com.outlook.bselzer1.chess.game.piece

import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.extend.*

/**
 * The name of the chess piece.
 */
enum class PieceName(val assetLocation: String)
{
    //TODO https://creativecommons.org/licenses/by/3.0/ for assets

    BISHOP("piece/bishop.png"),
    KING("piece/king.png"),
    KNIGHT("piece/knight.png"),
    PAWN("piece/pawn.png"),
    QUEEN("piece/queen.png"),
    ROOK("piece/rook.png");

    companion object
    {
        /**
         * Irrelevant position for initialization.
         */
        private val DUMMY_POSITION = Position(-1, -1)
    }

    /**
     * @return a new piece matching the [PieceName] that represents the promotion of [piece]
     */
    fun promoteFrom(piece: Piece<*>): Piece<*>
    {
        val promotedPiece = when (this)
        {
            BISHOP -> Bishop(piece.color, DUMMY_POSITION, piece.board)
            KING -> King(piece.color, DUMMY_POSITION, piece.board)
            KNIGHT -> Knight(piece.color, DUMMY_POSITION, piece.board)
            PAWN -> Pawn(piece.color, DUMMY_POSITION, piece.board)
            QUEEN -> Queen(piece.color, DUMMY_POSITION, piece.board)
            ROOK -> Rook(piece.color, DUMMY_POSITION, piece.board)
        }

        //Retain the id to be able to track the promoted piece.
        promotedPiece.id = piece.id

        //Set the actual position in order to apply the setter.
        promotedPiece.position = piece.position
        return promotedPiece
    }
}