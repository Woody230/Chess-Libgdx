import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.extend.WesternBoard
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.game.piece.extend.Pawn
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

/**
 * Tests that promotions are properly handled.
 */
class PromotionTests
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
     * Tests [Pawn] promotions on the last row of the opposite of the board.
     */
    @ParameterizedTest
    @EnumSource(value = PlayerColor::class, names = ["BLACK", "WHITE"])
    fun pawn(color: PlayerColor)
    {
        val oldPosition = Position(4, if (color == PlayerColor.BLACK) 6 else 1)
        val newPosition = Position(4, if (color == PlayerColor.BLACK) 7 else 0)
        val pawn = Pawn(color, oldPosition, board)

        for (successor in pawn.promotion!!.successors)
        {
            //Reset
            pawn.position = oldPosition
            board.clear()

            addPiece(board, pawn)
            board.move(oldPosition, newPosition) //Move -> set position -> set eligibility
            board.promotePiece(pawn, successor)

            board.getPieceAt(newPosition)!!.name shouldBeEqualTo successor
        }
    }
}