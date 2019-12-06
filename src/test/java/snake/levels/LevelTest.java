package snake.levels;

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
import snake.Snake;
import snake.User;
import snake.gui.DeathScreen;
import snake.gui.Game;
import snake.squares.ColouredSquare;
import snake.squares.Square;



public abstract class LevelTest {
    private transient Game game;
    private transient Level level;
    private transient float width = 800;
    private transient float height = 800;
    private transient int stepsize = 50;
    private transient List<Square> snakeBody;
    private transient List<Square> squares;
    private transient int counter;

    public abstract Level makeLevel(Game game, int stepsize);

    public Level getLevel() {
        return this.level;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public Game getGame() {
        return this.game;
    }

    public int getStepsize() {
        return this.stepsize;
    }

    @BeforeEach
    void setUp() {
        this.game = mock(Game.class);
        User user = new User();
        doReturn(width).when(game).getWidth();
        doReturn(height).when(game).getHeight();
        doReturn(user).when(game).getUser();

        this.level = makeLevel(game, stepsize);
        this.snakeBody = level.getSnakeBody();
        this.squares = level.getSquares();
        this.counter = 0;
    }

    @Test
    void testConstructor() {
        assertNotNull(level);
        assertEquals(width, game.getWidth());
        assertEquals(height, game.getHeight());
        assertEquals(stepsize, level.getStepSize());
        assertEquals(0, level.getScore());
    }

    @Test
    public void testSnakeSetup() {
        assertEquals(level.getSnakeBody().size(), level.getSnakeSize());

        counter = 0;
        for (int j = 0; j < height && counter < level.getSnakeSize(); j += stepsize) {
            assertEquals(new ColouredSquare(0, j, stepsize - 2, stepsize - 2,
                    null), snakeBody.get(counter++));
        }
    }

    @Test
    public void testBoardSetup() {
        counter = 0;
        for (int i = 0; i < width; i += stepsize) {
            //Go through all of the rows
            for (int j = 0; j < height; j += stepsize) {
                Square testSquare = new ColouredSquare(i, j, stepsize - 2, stepsize - 2, null);
                assertEquals(testSquare, squares.get(counter++));
            }
        }
    }

    @Test
    public void testMoveUp() {
        Square head = new ColouredSquare(width / 2, height / 2, stepsize - 2, stepsize - 2, null);
        Square tail = new ColouredSquare(width / 2 - stepsize, height / 2, stepsize - 2,
                stepsize - 2, null);
        List<Square> body = new ArrayList<>(Arrays.asList(head, tail));

        level.setSnake(new Snake(head, tail, body));

        level.createSnack((int) width, (int) height);
        level.moveUp();

        Square newHead = new ColouredSquare(head.getXvalue(), head.getYvalue() + stepsize,
                head.getWidth(), head.getHeight(), null);

        assertEquals(head, level.getSnake().getTail());
        assertEquals(newHead, level.getSnake().getHead());
        assertTrue(level.getSnake().getBody().contains(newHead));
        assertTrue(level.getSquares().contains(tail));
        assertFalse(level.getSnake().getBody().contains(tail));
    }

    @Test
    public void testMoveDown() {
        Square head = new ColouredSquare(width / 2, height / 2, stepsize - 2, stepsize - 2, null);
        Square tail = new ColouredSquare(width / 2 - stepsize, height / 2, stepsize - 2,
                stepsize - 2, null);
        List<Square> body = new ArrayList<>(Arrays.asList(head, tail));

        level.setSnake(new Snake(head, tail, body));

        level.createSnack((int) width, (int) height);
        level.moveDown();

        Square newHead = new ColouredSquare(head.getXvalue(), head.getYvalue() - stepsize,
                head.getWidth(), head.getHeight(), null);

        assertEquals(head, level.getSnake().getTail());
        assertEquals(newHead, level.getSnake().getHead());
        assertTrue(level.getSnake().getBody().contains(newHead));
        assertTrue(level.getSquares().contains(tail));
        assertFalse(level.getSnake().getBody().contains(tail));
    }

    @Test
    public void testMoveRight() {
        Square head = new ColouredSquare(width / 2, height / 2, stepsize - 2, stepsize - 2, null);
        Square tail = new ColouredSquare(width / 2 - stepsize, height / 2,
                stepsize - 2, stepsize - 2, null);
        List<Square> body = new ArrayList<>(Arrays.asList(head, tail));

        level.setSnake(new Snake(head, tail, body));

        level.createSnack((int) width, (int) height);
        level.moveRight();

        Square newHead = new ColouredSquare(head.getXvalue() + stepsize, head.getYvalue(),
                head.getWidth(), head.getHeight(), null);

        assertEquals(head, level.getSnake().getTail());
        assertEquals(newHead, level.getSnake().getHead());
        assertTrue(level.getSnake().getBody().contains(newHead));
        assertTrue(level.getSquares().contains(tail));
        assertFalse(level.getSnake().getBody().contains(tail));
    }

    @Test
    public void testMoveLeft() {
        Square head = new ColouredSquare(width / 2, height / 2, stepsize - 2, stepsize - 2, null);
        Square tail = new ColouredSquare(width / 2 + stepsize, height / 2,
                stepsize - 2, stepsize - 2, null);
        List<Square> body = new ArrayList<>(Arrays.asList(head, tail));

        level.setSnake(new Snake(head, tail, body));

        level.createSnack((int) width, (int) height);
        level.moveLeft();

        Square newHead = new ColouredSquare(head.getXvalue() - stepsize, head.getYvalue(),
                head.getWidth(), head.getHeight(), null);

        assertEquals(head, level.getSnake().getTail());
        assertEquals(newHead, level.getSnake().getHead());
        assertTrue(level.getSnake().getBody().contains(newHead));
        assertTrue(level.getSquares().contains(tail));
        assertFalse(level.getSnake().getBody().contains(tail));
    }


    @Test
    public void testMoveCreatesSnack() {
        assertNull(level.getSnack());

        level.move(0f, 0f);

        assertNotNull(level.getSnack());
    }

    @Test
    public void testValidMoveNoSnack() {
        Square prevHead = level.getSnake().getHead();
        final Square prevTail = level.getSnake().getTail();
        Square newTail = level.getSnake().getBody().get(1);

        level.createSnack((int) width, (int) height);
        level.move(prevHead.getXvalue() + stepsize, prevHead.getYvalue() + stepsize);

        assertEquals(newTail, level.getSnake().getTail());
        assertEquals(
                new ColouredSquare(prevHead.getXvalue() + stepsize, prevHead.getYvalue() + stepsize,
                        stepsize - 2, stepsize - 2, null), level.getSnake().getHead());
        assertTrue(level.getSnake().getBody().contains(prevHead));
        assertFalse(level.getSnake().getBody().contains(prevTail));
    }

    @Test
    public void testValidMoveSnack() {
        int prevScore = level.getScore();
        Square prevHead = level.getSnake().getHead();
        final Square prevTail = level.getSnake().getTail();

        assertEquals(prevScore, 0);

        level.createSnack(0, (int) ((prevHead.getYvalue() + stepsize) / stepsize));
        level.move(prevHead.getXvalue(), prevHead.getYvalue() + stepsize);

        assertEquals(level.getScore(), 10);
        assertEquals(prevTail, level.getSnake().getTail());
        assertEquals(
                new ColouredSquare(0, prevHead.getYvalue() + stepsize,
                        stepsize - 2, stepsize - 2, null), level.getSnake().getHead());
        assertTrue(level.getSnake().getBody().contains(prevHead));
        assertTrue(level.getSnake().getBody().contains(prevTail));
    }

    @Test
    public void testInvalidSelfMove() {
        Square head = level.getHead();
        level.createSnack((int) width, (int) height);

        level.move(0f, head.getYvalue() - stepsize);
        verify(game, new Times(1)).setScreen(any(DeathScreen.class));
    }

    @Test
    public void testCreateSnackNotInBody() {
        int random1 = 50;
        int random2 = 50;
        level.createSnack(random1, random2);

        Square snack = level.getSnack();

        assertEquals(random1 * stepsize + stepsize / 5, snack.getXvalue());
        assertEquals(random1 * stepsize + stepsize / 5, snack.getYvalue());
        assertEquals(3 * stepsize / 5, snack.getWidth());
        assertEquals(3 * stepsize / 5, snack.getHeight());
    }

    @Test
    public void testCreateSnackInBody() {
        int random1 = (int) snakeBody.get(0).getXvalue();
        int random2 = (int) snakeBody.get(0).getYvalue();
        level.createSnack(random1, random2);

        Square snack = level.getSnack();

        //could be on the same x value as long as it's not at the same y value and vice versa
        assertTrue(random1 * stepsize + stepsize / 5 != snack.getXvalue()
                || random1 * stepsize + stepsize / 5 != snack.getYvalue());
        assertEquals(3 * stepsize / 5, snack.getWidth());
        assertEquals(3 * stepsize / 5, snack.getHeight());
    }

    @Test
    public void testSetPause() {
        assertEquals(false, level.isPaused());
        level.setPaused();
        assertEquals(true, level.isPaused());
        level.setPaused();
        assertEquals(false, level.isPaused());
    }

    @Test
    public void testValidMovePaused() {
        final Square prevHead = level.getSnake().getHead();
        final Square prevTail = level.getSnake().getTail();

        assertEquals(false, level.isPaused());
        level.setPaused();
        assertEquals(true, level.isPaused());

        level.move(prevHead.getXvalue() + stepsize, prevHead.getYvalue() + stepsize);

        assertEquals(prevTail, level.getSnake().getTail());
        assertEquals(prevHead, level.getSnake().getHead());
        assertTrue(level.getSnake().getBody().contains(prevHead));
        assertTrue(level.getSnake().getBody().contains(prevTail));
    }

    @Test
    public void testValidMovePausedUnpaused() {
        final Square prevTail = level.getSnake().getTail();
        final Square prevHead = level.getSnake().getHead();
        final Square newTail = level.getSnake().getBody().get(1);

        testSetPause();

        level.createSnack((int) width, (int) height);
        level.move(prevHead.getXvalue() + stepsize, prevHead.getYvalue() + stepsize);

        assertEquals(newTail, level.getSnake().getTail());
        assertEquals(
                new ColouredSquare(prevHead.getXvalue() + stepsize, prevHead.getYvalue() + stepsize,
                        stepsize - 2, stepsize - 2, null), level.getSnake().getHead());
        assertTrue(level.getSnake().getBody().contains(prevHead));
        assertFalse(level.getSnake().getBody().contains(prevTail));
    }

    @Test
    public void testGetHead() {
        assertEquals(snakeBody.get(snakeBody.size() - 1), level.getHead());
    }

    @Test
    public void testGetTail() {
        assertEquals(0, level.getScore());
    }

    @Test
    public void testGetSnakeSize() {
        assertEquals(9, level.getSnakeSize());
    }

    @Test
    public void testGetGame() {
        assertEquals(game, level.getGame());
    }
}
