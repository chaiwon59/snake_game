import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;

public class BoardTest {
    private transient Board board;
    private transient Game game;

    private transient float width = 800;
    private transient float height = 800;
    private transient int stepsize = 50;

    private transient List<Square> snakeBody;
    private transient List<Square> squares;
    private transient int counter;

    @BeforeEach
    void setUp() {
        this.game = mock(Game.class);
        doReturn(width).when(game).getWidth();
        doReturn(height).when(game).getHeight();

        this.board = new Board(stepsize, game);
        this.snakeBody = board.getSnakeBody();
        this.squares = board.getSquares();
        this.counter = 0;
    }

    @Test
    void testConstructor() {
        assertNotNull(board);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
        assertEquals(stepsize, board.getStepSize());
        assertEquals(0, board.getScore());
    }

    @Test
    public void testSnakeSetup() {
        assertEquals(board.getSnakeBody().size(), board.getInitSnakeSize());

        counter = 0;
        for (int j = 0; j < height && counter < board.getInitSnakeSize(); j += stepsize) {
            assertEquals(new Square(0, j, stepsize - 2, stepsize - 2), snakeBody.get(counter++));
        }
    }

    @Test
    public void testBoardSetup() {
        counter = 0;
        for (int i = 0; i < width; i += stepsize) {
            //Go through all of the rows
            for (int j = 0; j < height; j += stepsize) {
                Square testSquare = new Square(i, j, stepsize - 2, stepsize - 2);
                if (!snakeBody.contains(testSquare)) {
                    assertEquals(testSquare, squares.get(counter++));
                }
            }
        }
    }

    @Test
    public void testMoveUp() {
        Square head = new Square(width / 2, height / 2, stepsize - 2, stepsize - 2);
        Square tail = new Square(width / 2 - stepsize, height / 2, stepsize - 2, stepsize - 2);
        List<Square> body = new ArrayList<>(Arrays.asList(head, tail));

        board.setSnake(new Snake(head, tail, body));

        board.createSnack((int) width, (int) height);
        board.moveUp();

        Square newHead = new Square(head.getXvalue(), head.getYvalue() + stepsize,
                head.getWidth(), head.getHeight());

        assertEquals(head, board.getSnake().getTail());
        assertEquals(newHead, board.getSnake().getHead());
        assertTrue(board.getSnake().getBody().contains(newHead));
        assertTrue(board.getSquares().contains(tail));
        assertFalse(board.getSnake().getBody().contains(tail));
        assertFalse(board.getSquares().contains(board.getSnake().getHead()));
    }

    @Test
    public void testMoveDown() {
        Square head = new Square(width / 2, height / 2, stepsize - 2, stepsize - 2);
        Square tail = new Square(width / 2 - stepsize, height / 2, stepsize - 2, stepsize - 2);
        List<Square> body = new ArrayList<>(Arrays.asList(head, tail));

        board.setSnake(new Snake(head, tail, body));

        board.createSnack((int) width, (int) height);
        board.moveDown();

        Square newHead = new Square(head.getXvalue(), head.getYvalue() - stepsize,
                head.getWidth(), head.getHeight());

        assertEquals(head, board.getSnake().getTail());
        assertEquals(newHead, board.getSnake().getHead());
        assertTrue(board.getSnake().getBody().contains(newHead));
        assertTrue(board.getSquares().contains(tail));
        assertFalse(board.getSnake().getBody().contains(tail));
        assertFalse(board.getSquares().contains(board.getSnake().getHead()));
    }

    @Test
    public void testMoveRight() {
        Square head = new Square(width / 2, height / 2, stepsize - 2, stepsize - 2);
        Square tail = new Square(width / 2 - stepsize, height / 2, stepsize - 2, stepsize - 2);
        List<Square> body = new ArrayList<>(Arrays.asList(head, tail));

        board.setSnake(new Snake(head, tail, body));

        board.createSnack((int) width, (int) height);
        board.moveRight();

        Square newHead = new Square(head.getXvalue() + stepsize, head.getYvalue(),
                head.getWidth(), head.getHeight());

        assertEquals(head, board.getSnake().getTail());
        assertEquals(newHead, board.getSnake().getHead());
        assertTrue(board.getSnake().getBody().contains(newHead));
        assertTrue(board.getSquares().contains(tail));
        assertFalse(board.getSnake().getBody().contains(tail));
        assertFalse(board.getSquares().contains(board.getSnake().getHead()));
    }

    @Test
    public void testMoveLeft() {
        Square head = new Square(width / 2, height / 2, stepsize - 2, stepsize - 2);
        Square tail = new Square(width / 2 + stepsize, height / 2, stepsize - 2, stepsize - 2);
        List<Square> body = new ArrayList<>(Arrays.asList(head, tail));

        board.setSnake(new Snake(head, tail, body));

        board.createSnack((int) width, (int) height);
        board.moveLeft();

        Square newHead = new Square(head.getXvalue() - stepsize, head.getYvalue(),
                head.getWidth(), head.getHeight());

        assertEquals(head, board.getSnake().getTail());
        assertEquals(newHead, board.getSnake().getHead());
        assertTrue(board.getSnake().getBody().contains(newHead));
        assertTrue(board.getSquares().contains(tail));
        assertFalse(board.getSnake().getBody().contains(tail));
        assertFalse(board.getSquares().contains(board.getSnake().getHead()));
    }


    @Test
    public void testMoveCreatesSnack() {
        assertNull(board.getSnack());

        board.move(0, 0);

        assertNotNull(board.getSnack());
    }

    @Test
    public void testValidMoveNoSnack() {
        Square prevHead = board.getSnake().getHead();
        final Square prevTail = board.getSnake().getTail();
        Square newTail = board.getSnake().getBody().get(1);

        board.createSnack((int) width, (int) height);
        board.move(prevHead.getXvalue() + stepsize, prevHead.getYvalue() + stepsize);

        assertEquals(newTail, board.getSnake().getTail());
        assertEquals(
                new Square(prevHead.getXvalue() + stepsize, prevHead.getYvalue() + stepsize,
                        stepsize - 2, stepsize - 2), board.getSnake().getHead());
        assertTrue(board.getSnake().getBody().contains(prevHead));
        assertFalse(board.getSnake().getBody().contains(prevTail));
        assertTrue(board.getSquares().contains(prevTail));
        assertFalse(board.getSquares().contains(board.getSnake().getHead()));
    }

    @Test
    public void testValidMoveSnack() {
        int prevScore = board.getScore();
        Square prevHead = board.getSnake().getHead();
        final Square prevTail = board.getSnake().getTail();

        assertEquals(prevScore, 0);

        board.createSnack(0, (int) ((prevHead.getYvalue() + stepsize) / stepsize));
        board.move(prevHead.getXvalue(), prevHead.getYvalue() + stepsize);

        assertEquals(board.getScore(), 10);
        assertEquals(prevTail, board.getSnake().getTail());
        assertEquals(
                new Square(0, prevHead.getYvalue() + stepsize,
                        stepsize - 2, stepsize - 2), board.getSnake().getHead());
        assertTrue(board.getSnake().getBody().contains(prevHead));
        assertTrue(board.getSnake().getBody().contains(prevTail));
        assertFalse(board.getSquares().contains(prevTail));
        assertFalse(board.getSquares().contains(board.getSnake().getHead()));
    }

    @Test
    public void testInvalidOutOfBounds() {
        board.createSnack((int) width, (int) height);

        board.move(width + stepsize, height + stepsize);
        verify(game, new Times(1)).setScreen(any(DeathScreen.class));
    }

    @Test
    public void testInvalidSelfMove() {
        Square head = board.getHead();
        board.createSnack((int) width, (int) height);

        board.move(0, head.getYvalue() - stepsize);
        verify(game, new Times(1)).setScreen(any(DeathScreen.class));
    }

    @Test
    public void testCreateSnackNotInBody() {
        int random1 = 50;
        int random2 = 50;
        board.createSnack(random1, random2);

        Square snack = board.getSnack();

        assertEquals(random1 * stepsize + stepsize / 5, snack.getXvalue());
        assertEquals(random1 * stepsize + stepsize / 5, snack.getYvalue());
        assertEquals(3 * stepsize / 5, snack.getWidth());
        assertEquals(3 * stepsize / 5, snack.getHeight());
    }

    @Test
    public void testCreateSnackInBody() {
        int random1 = (int) snakeBody.get(0).getXvalue();
        int random2 = (int) snakeBody.get(0).getYvalue();
        board.createSnack(random1, random2);

        Square snack = board.getSnack();

        //could be on the same x value as long as it's not at the same y value and vice versa
        assertTrue(random1 * stepsize + stepsize / 5 != snack.getXvalue()
                || random1 * stepsize + stepsize / 5 != snack.getYvalue());
        assertEquals(3 * stepsize / 5, snack.getWidth());
        assertEquals(3 * stepsize / 5, snack.getHeight());
    }

    @Test
    public void testSetPause() {
        assertEquals(false, board.isPaused());
        board.setPaused();
        assertEquals(true, board.isPaused());
        board.setPaused();
        assertEquals(false, board.isPaused());
    }

    @Test
    public void testValidMovePaused() {
        final Square prevHead = board.getSnake().getHead();
        final Square prevTail = board.getSnake().getTail();

        assertEquals(false, board.isPaused());
        board.setPaused();
        assertEquals(true, board.isPaused());

        board.move(prevHead.getXvalue() + stepsize, prevHead.getYvalue() + stepsize);

        assertEquals(prevTail, board.getSnake().getTail());
        assertEquals(prevHead, board.getSnake().getHead());
        assertTrue(board.getSnake().getBody().contains(prevHead));
        assertTrue(board.getSnake().getBody().contains(prevTail));
        assertFalse(board.getSquares().contains(prevTail));
        assertFalse(board.getSquares().contains(board.getSnake().getHead()));
    }

    @Test
    public void testValidMovePausedUnpaused() {
        final Square prevTail = board.getSnake().getTail();
        final Square prevHead = board.getSnake().getHead();
        final Square newTail = board.getSnake().getBody().get(1);

        testSetPause();

        board.createSnack((int) width, (int) height);
        board.move(prevHead.getXvalue() + stepsize, prevHead.getYvalue() + stepsize);

        assertEquals(newTail, board.getSnake().getTail());
        assertEquals(
                new Square(prevHead.getXvalue() + stepsize, prevHead.getYvalue() + stepsize,
                        stepsize - 2, stepsize - 2), board.getSnake().getHead());
        assertTrue(board.getSnake().getBody().contains(prevHead));
        assertFalse(board.getSnake().getBody().contains(prevTail));
        assertTrue(board.getSquares().contains(prevTail));
        assertFalse(board.getSquares().contains(board.getSnake().getHead()));
    }

    @Test
    public void testGetHead() {
        assertEquals(snakeBody.get(snakeBody.size() - 1), board.getHead());
    }

    @Test
    public void testGetTail() {
        assertEquals(0, board.getScore());
    }

    @Test
    public void testGetSnakeSize() {
        assertEquals(9, board.getInitSnakeSize());
    }

}
