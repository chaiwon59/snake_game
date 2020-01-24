package snake.integration.gamelevel;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.Dao;
import snake.Snake;
import snake.User;
import snake.games.Game;
import snake.games.levels.Level;
import snake.gui.LauncherClass;
import snake.squares.Square;

public abstract class GameLevel {
    private transient Game game;
    private transient Level level;

    private transient Snake player1;
    private transient Square head;
    private transient Square tail;

    private transient User user;
    private transient Dao dao;

    private transient LauncherClass launcher;
    private transient int stepSize = 50;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    public void setUp() {
        this.launcher = mock(LauncherClass.class);
        doReturn(800f).when(launcher).getWidth();
        doReturn(800f).when(launcher).getHeight();

        this.dao = mock(Dao.class);

        this.player1 = mock(Snake.class);
        this.head = mock(Square.class);
        this.tail = mock(Square.class);
        doReturn(head).when(player1).getHead();
        doReturn(tail).when(player1).getTail();

        this.user = mock(User.class);
        doReturn(user).when(launcher).getUser();
        doReturn(isTypeLevel()).when(user).isNoWalls();

        this.game = makeGame(launcher, stepSize);
        this.level = spy(makeLevel(game, launcher, stepSize));
        game.setLevel(level);
    }

    public abstract Game makeGame(LauncherClass launcher, int stepSize);

    public abstract Level makeLevel(Game game, LauncherClass launcher, int stepSize);

    public abstract boolean isTypeLevel();

    @Test
    public void testMoveUp() {
        final float newX = 50;
        final float newY = 100;
        doReturn(50f).when(head).getXvalue();
        doReturn(50f).when(head).getYvalue();
        doReturn(true).when(player1).moveSnake(newX, newY);

        assertFalse(level.isPaused());
        assertNull(game.getSnack());

        game.moveUp(player1);

        assertFalse(level.isPaused());
        assertNotNull(game.getSnack());

        verify(level, times(1)).checkValidity(newX, newY);
        verify(level, times(1)).move(newX, newY, player1);
        verify(level, times(1)).moveUp(player1);
    }

    @Test
    public void testMoveDown() {
        final float newX = 50;
        final float newY = 0;
        doReturn(50f).when(head).getXvalue();
        doReturn(50f).when(head).getYvalue();
        doReturn(true).when(player1).moveSnake(newX, newY);

        assertFalse(level.isPaused());
        assertNull(game.getSnack());

        game.moveDown(player1);

        assertFalse(level.isPaused());
        assertNotNull(game.getSnack());

        verify(level, times(1)).checkValidity(newX, newY);
        verify(level, times(1)).move(newX, newY, player1);
        verify(level, times(1)).moveDown(player1);
    }

    @Test
    public void testMoveLeft() {
        final float newX = 0;
        final float newY = 50;
        doReturn(50f).when(head).getXvalue();
        doReturn(50f).when(head).getYvalue();
        doReturn(true).when(player1).moveSnake(newX, newY);

        assertFalse(level.isPaused());
        assertNull(game.getSnack());

        game.moveLeft(player1);

        assertFalse(level.isPaused());
        assertNotNull(game.getSnack());

        verify(level, times(1)).checkValidity(newX, newY);
        verify(level, times(1)).move(newX, newY, player1);
        verify(level, times(1)).moveLeft(player1);
    }

    @Test
    public void testMoveRight() {
        final float newX = 100;
        final float newY = 50;
        doReturn(50f).when(head).getXvalue();
        doReturn(50f).when(head).getYvalue();
        doReturn(true).when(player1).moveSnake(newX, newY);

        assertFalse(level.isPaused());
        assertNull(game.getSnack());

        game.moveRight(player1);

        assertFalse(level.isPaused());
        assertNotNull(game.getSnack());

        verify(level, times(1)).checkValidity(newX, newY);
        verify(level, times(1)).move(newX, newY, player1);
        verify(level, times(1)).moveRight(player1);
    }
}
