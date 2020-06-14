package com.outlook.bselzer1.chess.game.piece.extend

import com.outlook.bselzer1.chess.game.board.BoardSize
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PieceName
import com.outlook.bselzer1.chess.game.piece.PlayerColor

class Pawn(color: PlayerColor, position: Position) : Piece(PieceName.PAWN, color, position)
{
    override fun getValidPositions(size: BoardSize): Collection<Position>
    {
        TODO("Not yet implemented")
    }
}