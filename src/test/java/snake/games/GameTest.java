package snake.games;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.Snake;
import snake.User;
import snake.games.levels.Level;
import snake.gui.LauncherClass;

public abstract class GameTest {
    transient Game game;
    transient int stepsize = 50;
    transient LauncherClass launcher;
    transient Level level;
    transient Snake snake;
    transient SnackBuilder builder;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    public void setUp() {
        this.launcher = mock(LauncherClass.class);
        User user = new User();
        doReturn(800f).when(launcher).getWidth();
        doReturn(800f).when(launcher).getHeight();
        doReturn(user).when(launcher).getUser();

        this.level = mock(Level.class);
        this.snake = mock(Snake.class);
        this.builder = mock(SnackBuilder.class);
        this.game = spy(makeGame(launcher, stepsize));
        game.level = level;
        game.player1 = snake;
        game.builder = builder;
    }

    public abstract Game makeGame(LauncherClass launcher, int stepsize);

    @Test
    public void testGameOver() {
        game.gameOver(game.player1);

        verify(launcher, times(1)).setScreen(any(Screen.class));
    }

    @Test
    public void testConstructor() {
        assertEquals(launcher, game.launcher);
        assertEquals(stepsize, game.stepSize);
        assertEquals(level, game.level);
        assertEquals(0, game.score1);
        assertEquals(false, game.gameIsOver);
    }

    @Test
    public void testMoveUp() {
        game.moveUp(snake);
        verify(level, times(1)).moveUp(snake);
    }

    @Test
    public void testMoveDown() {
        game.moveDown(snake);
        verify(level, times(1)).moveDown(snake);
    }

    @Test
    public void testMoveLeft() {
        game.moveLeft(snake);
        verify(level, times(1)).moveLeft(snake);
    }

    @Test
    public void testMoveRight() {
        game.moveRight(snake);
        verify(level, times(1)).moveRight(snake);
    }

    @Test
    public void testPaused() {
        assertEquals(false, game.isPaused());
        game.setPaused();
        verify(level, times(1)).setPaused();
    }

    @Test
    public void testGetPlayer1Head() {
        game.getPlayer1Head();
        verify(snake, times(1)).getHead();
    }

    @Test
    public void testGetPlayer1Body() {
        game.getPlayer1Body();
        verify(snake, times(1)).getBody();
    }

    @Test
    public void testGetSquares() {
        game.getSquares();
        verify(level, times(1)).getSquares();
    }

    @Test
    public void testGetScore1() {
        assertEquals(0, game.getScore1());

        game.score1 += 10;

        assertEquals(10, game.getScore1());
    }

    @Test
    public void testGetPlayer1() {
        assertEquals(snake, game.getPlayer1());
    }

    @Test
    public void testGetLauncher() {
        assertEquals(launcher, game.getLauncher());
    }

    @Test
    public void testGetSnack() {
        game.getSnack();
        verify(builder, times(1)).getSnack();
    }

    @Test
    public void testGetSnackSquare() {
        game.getSnackSquare();
        verify(builder, times(1)).getSnackSquare();
    }

    @Test
    public void testGetGameIsOver() {
        assertEquals(false, game.getGameIsOver());

        game.gameIsOver = true;

        assertEquals(true, game.getGameIsOver());
    }
}
