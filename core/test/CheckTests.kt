import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.extend.WesternBoard
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.board.move.PositionFlag
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.game.piece.extend.Bishop
import com.outlook.bselzer1.chess.game.piece.extend.King
import com.outlook.bselzer1.chess.game.piece.extend.Queen
import com.outlook.bselzer1.chess.game.piece.extend.Rook
import com.outlook.bselzer1.chess.sharedfunctions.extension.contentEquals
import org.junit.jupiter.api.Test

/**
 * Tests that checks and checkmates are properly determined.
 */
class CheckTests
{
    /**
     * The board.
     */
    private val board: Board = WesternBoard()

    /**
     * Add piece method.
     */
    private val addPiece = Board::class.java.getDeclaredMethod("addPiece", Piece::class.java)

    init
    {
        //Needed since addPiece() is protected.
        addPiece.isAccessible = true
    }

    /**
     * Tests that the valid positions will not keep the king in check.
     */
    @Test
    fun isCurrentlyChecked_StopsCheck()
    {
        val king = King(PlayerColor.BLACK, Position(0, 2), board)
        val queen = Queen(PlayerColor.BLACK, Position(4, 3), board)

        addPiece(board, king)
        addPiece(board, queen)
        addPiece(board, Bishop(PlayerColor.WHITE, Position(4, 6), board))
        addPiece(board, King(PlayerColor.WHITE, Position(4, 7), board))

        assert(king.getPositions(PositionFlag.VALIDATE).contentEquals(listOf(
                Position(0, 3),
                Position(1, 2),
                Position(1, 1),
                Position(0, 1)
        )))

        assert(queen.getPositions(PositionFlag.VALIDATE).contentEquals(listOf(
                Position(4, 6),
                Position(1, 3)
        )))
    }

    /**
     * Tests that the valid positions will prevent a move that would put the king in check.
     */
    @Test
    fun wouldBeChecked_PreventsMove()
    {
        val rook = Rook(PlayerColor.BLACK, Position(1, 6), board)

        addPiece(board, King(PlayerColor.BLACK, Position(0, 7), board))
        addPiece(board, rook)
        addPiece(board, Queen(PlayerColor.WHITE, Position(4, 3), board))
        addPiece(board, King(PlayerColor.WHITE, Position(7, 0), board))

        assert(rook.getPositions(PositionFlag.VALIDATE).isEmpty())
    }

    /**
     * Tests a valid checkmate situation.
     */
    @Test
    fun isCheckmated()
    {
        addPiece(board, King(PlayerColor.BLACK, Position(0, 0), board))
        addPiece(board, Queen(PlayerColor.WHITE, Position(1, 1), board))
        addPiece(board, Bishop(PlayerColor.WHITE, Position(2, 2), board))
        addPiece(board, King(PlayerColor.WHITE, Position(7, 7), board))

        assert(board.isCheckmated(PlayerColor.BLACK))
    }

    /**
     * Tests that a player is not checkmated when the king has valid moves.
     */
    @Test
    fun isNotCheckmated()
    {
        addPiece(board, King(PlayerColor.BLACK, Position(0, 0), board))
        addPiece(board, Bishop(PlayerColor.WHITE, Position(1, 1), board))
        addPiece(board, Queen(PlayerColor.WHITE, Position(2, 1), board))
        addPiece(board, King(PlayerColor.WHITE, Position(7, 7), board))

        assert(!board.isCheckmated(PlayerColor.BLACK))
    }
}