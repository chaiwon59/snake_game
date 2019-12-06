package snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.gui.Game;
import snake.squares.ColouredSquare;
import snake.squares.Square;

public class BoardTest {
    private transient Board board;
    private transient Game game;
    private transient int squareSize;
    private transient int stepSize;
    private transient float height;
    private transient float width;
    private transient int counter;

    /**
     * Setup for the test class.
     */
    @BeforeEach
    public void setUp() {
        this.game = mock(Game.class);
        this.squareSize = 48;
        this.stepSize = 50;
        this.height = 800;
        this.width = 800;

        doReturn(height).when(game).getHeight();
        doReturn(height).when(game).getWidth();

        board = new Board(game, squareSize, stepSize);
    }

    @Test
    public void createBoard() {
        assertEquals(height / stepSize * width / stepSize, board.createBoard().size());

        counter = 0;
        for (int i = 0; i < width; i += stepSize) {
            //Go through all of the rows
            for (int j = 0; j < height; j += stepSize) {
                Square testSquare = new ColouredSquare(i, j, stepSize - 2, stepSize - 2, null);
                assertEquals(testSquare, board.getSquares().get(counter++));
            }
        }
    }
}
