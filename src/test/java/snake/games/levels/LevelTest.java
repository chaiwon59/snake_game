package snake.games.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import snake.Dao;
import snake.Snake;
import snake.User;
import snake.games.Game;
import snake.games.SnackBuilder;
import snake.gui.LauncherClass;
import snake.squares.ColouredSquare;
import snake.squares.Square;


public abstract class LevelTest {
    transient LauncherClass launcherClass;
    transient Level level;
    transient float width = 800;
    transient float height = 800;
    transient int stepsize = 50;
    transient List<Square> squares;
    transient int counter;
    transient Dao dao;

    transient Snake snake;

    transient Game game;

    transient SnackBuilder builder;

    public abstract Level makeLevel(Game game, LauncherClass launcherClass, int stepsize);

    public Level getLevel() {
        return this.level;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public LauncherClass getLauncherClass() {
        return this.launcherClass;
    }

    public int getStepsize() {
        return this.stepsize;
    }

    @BeforeEach
    void setUp() {
        this.game = mock(Game.class);
        this.launcherClass = mock(LauncherClass.class);
        User user = new User();
        doReturn(width).when(launcherClass).getWidth();
        doReturn(height).when(launcherClass).getHeight();
        doReturn(user).when(launcherClass).getUser();

        this.level = makeLevel(game, launcherClass, stepsize);
        this.squares = level.getSquares();
        this.counter = 0;

        Square head = new Square(width / 2, height / 2, stepsize - 2, stepsize - 2);
        Square tail = new Square(width / 2 - stepsize, height / 2, stepsize - 2,
                stepsize - 2);
        List<Square> body = new ArrayList<>(Arrays.asList(head, tail));
        this.snake = new Snake(head, tail, body);
        this.dao = mock(Dao.class);


        this.builder = new SnackBuilder(launcherClass, stepsize, stepsize);
        builder.createSnack(snake.getBody());

        doAnswer(invocation -> builder.getSnackSquare()).when(game).getSnackSquare();
        doAnswer(invocation -> builder.getSnack()).when(game).getSnack();
        doAnswer(invocation -> {
            builder.createSnack(snake.getBody());
            return null;
        }).when(game).createSnack();
    }

    @Test
    void testConstructor() {
        assertNotNull(level);
        assertEquals(width, launcherClass.getWidth());
        assertEquals(height, launcherClass.getHeight());
        assertEquals(stepsize, level.getStepSize());
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
        final Square oldHead = snake.getHead();
        final Square oldTail = snake.getTail();

        level.moveUp(snake);

        Square newHead = new Square(oldHead.getXvalue(),
                oldHead.getYvalue() + stepsize, stepsize - 2, stepsize - 2);

        assertEquals(newHead, snake.getHead());
        assertEquals(oldHead, snake.getTail());
        assertEquals(true, snake.getBody().contains(newHead));
        assertEquals(false, snake.getBody().contains(oldTail));
    }

    @Test
    public void testMoveDown() {
        final Square oldHead = snake.getHead();
        final Square oldTail = snake.getTail();

        level.moveDown(snake);

        Square newHead = new Square(oldHead.getXvalue(),
                oldHead.getYvalue() - stepsize, stepsize - 2, stepsize - 2);

        assertEquals(newHead, snake.getHead());
        assertEquals(oldHead, snake.getTail());
        assertEquals(true, snake.getBody().contains(newHead));
        assertEquals(false, snake.getBody().contains(oldTail));
    }

    @Test
    public void testMoveRight() {
        final Square oldHead = snake.getHead();
        final Square oldTail = snake.getTail();

        level.moveRight(snake);

        Square newHead = new Square(oldHead.getXvalue() + stepsize,
                oldHead.getYvalue(), stepsize - 2, stepsize - 2);

        assertEquals(newHead, snake.getHead());
        assertEquals(oldHead, snake.getTail());
        assertEquals(true, snake.getBody().contains(newHead));
        assertEquals(false, snake.getBody().contains(oldTail));
    }

    @Test
    public void testMoveLeft() {
        final Square oldHead = snake.getHead();

        level.moveLeft(snake);

        Square newHead = new Square(oldHead.getXvalue() - stepsize,
                oldHead.getYvalue(), stepsize - 2, stepsize - 2);

        assertEquals(newHead, snake.getHead());
        assertEquals(oldHead, snake.getTail());
        assertEquals(true, snake.getBody().contains(newHead));
    }

    @Test
    public void testPause() {
        assertEquals(false, level.isPaused());

        level.setPaused();

        assertEquals(true, level.isPaused());
    }

    @Test
    public void testPausedNoMove() {
        testPause();

        final Square oldHead = snake.getHead();
        final Square oldTail = snake.getTail();
        final List<Square> oldBody = snake.getBody();

        level.moveUp(snake);

        assertEquals(oldHead, snake.getHead());
        assertEquals(oldTail, snake.getTail());
        assertEquals(oldBody, snake.getBody());
    }

    @Test
    public void testMakeSnack() {
        this.builder = new SnackBuilder(launcherClass, stepsize, stepsize);

        assertNull(game.getSnack());

        level.moveUp(snake);

        assertNotNull(game.getSnack());
    }

    @Test
    public void testValidMoveNoSnack() {
        final int prevSize = snake.getBody().size();
        final Square prevHead = snake.getHead();
        final Square oldTail = snake.getTail();

        builder.createSnack((int) width, (int) height, new ArrayList<>());
        level.move(prevHead.getXvalue(), prevHead.getYvalue() + stepsize, snake);

        assertEquals(false, snake.getBody().contains(oldTail));
        assertEquals(prevSize, snake.getBody().size());
    }

    @Test
    public void testValidMoveSnack() {
        final Square prevHead = snake.getHead();
        final Square prevTail = snake.getTail();
        final int prevSize = snake.getBody().size();

        builder.createSnack((int) prevHead.getXvalue() / stepsize,
                (int) (prevHead.getYvalue() + stepsize) / stepsize, snake.getBody());

        level.move(prevHead.getXvalue(), prevHead.getYvalue() + stepsize, snake);

        assertEquals(prevTail, snake.getTail());
        assertEquals(prevSize + 1, snake.getBody().size());
        verify(game, times(1)).updateScore(any(Snake.class));
    }
}
