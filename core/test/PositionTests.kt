import com.outlook.bselzer1.chess.game.board.Board
import com.outlook.bselzer1.chess.game.board.extend.WesternBoard
import com.outlook.bselzer1.chess.game.board.move.Direction
import com.outlook.bselzer1.chess.game.board.move.Position
import com.outlook.bselzer1.chess.game.piece.Piece
import com.outlook.bselzer1.chess.game.piece.PlayerColor
import com.outlook.bselzer1.chess.game.piece.extend.*
import com.outlook.bselzer1.chess.sharedfunctions.extension.contentEquals
import org.junit.jupiter.api.Test

/**
 * Tests that valid positions are being generated.
 */
class PositionTests
{
    companion object
    {
        /**
         * The position to use for the piece being tested.
         */
        val TEST_PIECE_POSITION = Position(3, 4)

        /**
         * The valid custom horizontal and vertical positions.
         */
        val CUSTOM_CARTESIAN = listOf(
                Position(3, 5),
                Position(4, 4),
                Position(5, 4),
                Position(6, 4),
                Position(7, 4),
                Position(3, 3),
                Position(3, 2),
                Position(3, 1)
        )

        /**
         * The valid blank horizontal and vertical positions.
         */
        val BLANK_CARTESIAN = listOf(
                Position(0, TEST_PIECE_POSITION.y),
                Position(1, TEST_PIECE_POSITION.y),
                Position(2, TEST_PIECE_POSITION.y),
                Position(4, TEST_PIECE_POSITION.y),
                Position(5, TEST_PIECE_POSITION.y),
                Position(6, TEST_PIECE_POSITION.y),
                Position(7, TEST_PIECE_POSITION.y),
                Position(TEST_PIECE_POSITION.x, 0),
                Position(TEST_PIECE_POSITION.x, 1),
                Position(TEST_PIECE_POSITION.x, 2),
                Position(TEST_PIECE_POSITION.x, 3),
                Position(TEST_PIECE_POSITION.x, 5),
                Position(TEST_PIECE_POSITION.x, 6),
                Position(TEST_PIECE_POSITION.x, 7)
        )

        /**
         * The valid custom diagonal positions.
         */
        val CUSTOM_DIAGONALS = listOf(
                Position(2, 5),
                Position(4, 5),
                Position(5, 6),
                Position(4, 3),
                Position(5, 2),
                Position(6, 1),
                Position(7, 0)
        )

        /**
         * The valid blank diagonal positions.
         */
        val BLANK_DIAGONALS = listOf(
                Position(0, 7),
                Position(1, 6),
                Position(2, 5),
                Position(4, 3),
                Position(5, 2),
                Position(6, 1),
                Position(7, 0),
                Position(0, 1),
                Position(1, 2),
                Position(2, 3),
                Position(4, 5),
                Position(5, 6),
                Position(6, 7)
        )

        /**
         * The valid custom king positions.
         */
        val CUSTOM_KING = listOf(
                Position(2, 5),
                Position(4, 5),
                Position(4, 4),
                Position(4, 3),
                Position(3, 3)
        )

        /**
         * The valid blank king positions.
         */
        val BLANK_KING = listOf(
                Position(2, 5),
                Position(3, 5),
                Position(4, 5),
                Position(4, 4),
                Position(4, 3),
                Position(3, 3),
                Position(2, 3),
                Position(2, 4)
        )

        /**
         * The valid custom knight positions.
         */
        val CUSTOM_KNIGHT = listOf(
                Position(2, 6),
                Position(4, 6),
                Position(5, 5),
                Position(5, 3),
                Position(2, 2),
                Position(1, 5)
        )

        /**
         * The valid blank knight positions.
         */
        val BLANK_KNIGHT = listOf(
                Position(2, 6),
                Position(4, 6),
                Position(5, 5),
                Position(5, 3),
                Position(4, 2),
                Position(2, 2),
                Position(1, 3),
                Position(1, 5)
        )

        /**
         * The valid custom pawn positions.
         */
        val CUSTOM_PAWN = listOf(
                Position(2, 5),
                Position(3, 5)
        )

        /**
         * The valid blank pawn positions when the piece has already moved.
         */
        val BLANK_PAWN_HAS_MOVED = listOf(
                Position(3, 5)
        )

        /**
         * The valid blank pawn positions when the piece has not already moved.
         */
        val BLANK_PAWN_NOT_MOVED = BLANK_PAWN_HAS_MOVED.plus(Position(3, 6))

        /**
         * The valid en passant pawn positions.
         */
        val EN_PASSANT = listOf(
                Position(3, 5),
                Position(4, 5)
        )

        /**
         * A map of the direction to whether or not the associated value should be positive.
         */
        var directionalities = mapOf(
                "left" to false,
                "right" to true,
                "up" to true,
                "down" to false
        )
    }

    /**
     * A board to use for custom initializations.
     */
    private val customBoard: Board = WesternBoard()

    /**
     * A blank board.
     */
    private val blankBoard: Board = WesternBoard()

    /**
     * Add piece method.
     */
    private val addPiece = Board::class.java.getDeclaredMethod("addPiece", Piece::class.java)

    init
    {
        //Needed since addPiece() is protected.
        addPiece.isAccessible = true

        addPiece(customBoard, King(PlayerColor.WHITE, Position(0, 0), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.WHITE, Position(3, 0), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.WHITE, Position(0, 1), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.WHITE, Position(3, 1), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.BLACK, Position(4, 2), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.BLACK, Position(1, 3), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.BLACK, Position(2, 3), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.WHITE, Position(5, 3), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.WHITE, Position(0, 4), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.BLACK, Position(2, 4), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.WHITE, Position(2, 5), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.BLACK, Position(1, 6), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.WHITE, Position(2, 6), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.BLACK, Position(3, 6), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.WHITE, Position(4, 6), customBoard))
        addPiece(customBoard, King(PlayerColor.BLACK, Position(0, 7), customBoard))
        addPiece(customBoard, Pawn(PlayerColor.BLACK, Position(6, 7), customBoard))
    }

    /**
     * Test that the [Direction] enums are properly declared.
     */
    @Test
    fun directionIncrements()
    {
        for (direction in Direction.values())
        {
            for (component in direction.name.split("_"))
            {
                val directionality = directionalities.filter { component.startsWith(it.key, true) }.entries.first()
                val directionIncrement = if (directionality.key == "left" || directionality.key == "right") direction.xIncrement else direction.yIncrement
                var componentIncrement = component.replaceFirst(directionality.key, "", true).toInt()

                //If this value is false then the direction should be negative.
                if (!directionality.value)
                {
                    componentIncrement *= -1
                }

                assert(componentIncrement == directionIncrement)
            }
        }
    }

    /**
     * Test the bishop movements.
     */
    @Test
    fun bishop()
    {
        var piece = Bishop(PlayerColor.BLACK, TEST_PIECE_POSITION, customBoard)
        addPiece(customBoard, piece)
        assert(piece.getValidPositions().contentEquals(CUSTOM_DIAGONALS))

        piece = Bishop(PlayerColor.BLACK, TEST_PIECE_POSITION, blankBoard)
        addPiece(blankBoard, piece)
        assert(piece.getPositions().contentEquals(BLANK_DIAGONALS))
    }

    /**
     * Test the king movements.
     */
    @Test
    fun king()
    {
        //Need to use the existing king since it would be the first one retrieved instead of a new king.
        customBoard.move(Position(0, 7), TEST_PIECE_POSITION)

        var piece = customBoard.getPieceAt(TEST_PIECE_POSITION)
        assert(piece!!.getValidPositions().contentEquals(CUSTOM_KING))

        piece = King(PlayerColor.BLACK, TEST_PIECE_POSITION, blankBoard)
        addPiece(blankBoard, piece)
        assert(piece.getPositions().contentEquals(BLANK_KING))
    }

    /**
     * Test the knight movements.
     */
    @Test
    fun knight()
    {
        var piece = Knight(PlayerColor.BLACK, TEST_PIECE_POSITION, customBoard)
        addPiece(customBoard, piece)
        assert(piece.getValidPositions().contentEquals(CUSTOM_KNIGHT))

        piece = Knight(PlayerColor.BLACK, TEST_PIECE_POSITION, blankBoard)
        addPiece(blankBoard, piece)
        assert(piece.getPositions().contentEquals(BLANK_KNIGHT))
    }

    @Test
    fun pawn()
    {
        var piece = Pawn(PlayerColor.BLACK, TEST_PIECE_POSITION, customBoard)
        addPiece(customBoard, piece)
        assert(piece.getValidPositions().contentEquals(CUSTOM_PAWN))

        piece = Pawn(PlayerColor.BLACK, TEST_PIECE_POSITION, blankBoard)
        addPiece(blankBoard, piece)
        assert(piece.getPositions().contentEquals(BLANK_PAWN_NOT_MOVED))

        blankBoard.clear()
        piece = Pawn(PlayerColor.BLACK, Position(-1, -1), blankBoard)
        piece.position = TEST_PIECE_POSITION //To set hasMoved
        addPiece(blankBoard, piece)
        assert(piece.getPositions().contentEquals(BLANK_PAWN_HAS_MOVED))

        //Test en passant after the has moved case since it does not make sense if the capturing piece has not moved.
        val enPassantCapture = Pawn(PlayerColor.WHITE, Position(4, 6), blankBoard)
        addPiece(blankBoard, enPassantCapture)
        blankBoard.move(enPassantCapture.position, Position(4, 4))
        assert(piece.getPositions().contentEquals(EN_PASSANT))
    }

    /**
     * Test the queen movements.
     */
    @Test
    fun queen()
    {
        var piece = Queen(PlayerColor.BLACK, TEST_PIECE_POSITION, customBoard)
        addPiece(customBoard, piece)
        assert(piece.getValidPositions().contentEquals(CUSTOM_CARTESIAN.plus(CUSTOM_DIAGONALS)))

        piece = Queen(PlayerColor.BLACK, TEST_PIECE_POSITION, blankBoard)
        addPiece(blankBoard, piece)
        assert(piece.getPositions().contentEquals(BLANK_CARTESIAN.plus(BLANK_DIAGONALS)))
    }

    /**
     * Test the rook movements.
     */
    @Test
    fun rook()
    {
        var piece = Rook(PlayerColor.BLACK, TEST_PIECE_POSITION, customBoard)
        addPiece(customBoard, piece)
        assert(piece.getValidPositions().contentEquals(CUSTOM_CARTESIAN))

        piece = Rook(PlayerColor.BLACK, TEST_PIECE_POSITION, blankBoard)
        addPiece(blankBoard, piece)
        assert(piece.getPositions().contentEquals(BLANK_CARTESIAN))
    }
}
