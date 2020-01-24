package snake.games.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import snake.Dao;
import snake.Player;
import snake.Snake;
import snake.User;
import snake.games.Game;
import snake.games.builders.SnackBuilder;
import snake.games.powerups.PowerUp;
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

    transient Preferences prefs;

    transient Player player;

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
        HeadlessNativesLoader.load();

        Gdx.files = Mockito.mock(Files.class);
        Gdx.app = Mockito.mock(Application.class);
        prefs = Mockito.mock(Preferences.class);

        Mockito.doReturn(prefs).when(Gdx.app).getPreferences(any(String.class));

        this.game = mock(Game.class);
        this.launcherClass = mock(LauncherClass.class);
        this.player = mock(Player.class);
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
        this.snake = spy(new Snake(head, tail, body));
        this.dao = mock(Dao.class);


        this.builder = new SnackBuilder(game, launcherClass, stepsize, stepsize);
        builder.createSnack();

        doAnswer(invocation -> builder.getSnackSquare()).when(game).getSnackSquare();
        doAnswer(invocation -> builder.getSnack()).when(game).getSnack();
        doAnswer(invocation -> {
            builder.createSnack();
            return null;
        }).when(game).createSnack();
        doReturn(player).when(snake).getPlayer();
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
        this.builder = new SnackBuilder(game, launcherClass, stepsize, stepsize);

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
        verify(snake, times(1)).ateSnack();
    }

    @Test
    public void testSnakeMoveIntoSelf() {
        assertFalse(level.died);

        final Square head = snake.getHead();
        level.move(head.getXvalue(), head.getYvalue(), snake);

        assertTrue(level.died);
    }

    @Test
    public void testSetPaused() {
        assertFalse(level.isPaused());

        level.setPaused();

        assertTrue(level.isPaused());

        level.setPaused();

        assertFalse(level.isPaused());
    }

    @Test
    public void testPowerUpNull() {
        testMoveUp();

        verify(game, times(1)).getPowerUp();
        verify(game, times(0)).resetNextPowerUpTime();
    }

    @Test
    public void testPowerUpActive() {
        PowerUp powerUp = mock(PowerUp.class);

        Square powerUpSquare = mock(Square.class);

        doReturn(powerUp).when(game).getPowerUp();
        doReturn(true).when(game).isActive();
        doReturn(powerUpSquare).when(game).getPowerUpSquare();

        testPowerUpNull();
    }

    @Test
    public void testPowerUpInActive() {
        PowerUp powerUp = mock(PowerUp.class);

        Square powerUpSquare = mock(Square.class);

        doReturn(powerUp).when(game).getPowerUp();
        doReturn(false).when(game).isActive();
        doReturn(powerUpSquare).when(game).getPowerUpSquare();

        testPowerUpNull();
    }

    @Test
    public void testPowerUpTaken() {
        PowerUp powerUp = mock(PowerUp.class);

        doReturn(powerUp).when(game).getPowerUp();
        doReturn(false).when(game).isActive();

        Square head = snake.getHead();
        Square newHead = new Square(
                head.getXvalue(), head.getYvalue() + stepsize, head.getWidth(), head.getHeight());
        doReturn(newHead).when(game).getPowerUpSquare();

        level.moveUp(snake);

        verify(powerUp, times(1)).apply(player);
        verify(game, times(1)).resetNextPowerUpTime();
    }
}
