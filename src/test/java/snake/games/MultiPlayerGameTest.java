package snake.games;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.Snake;
import snake.gui.LauncherClass;
import snake.gui.deathscreens.MultiPlayerDeathScreen;
import snake.squares.Square;

public class MultiPlayerGameTest extends GameTest {

    public Game makeGame(LauncherClass launcher, int stepsize) {
        return new MultiPlayerGame(launcher, stepsize);
    }

    private transient MultiPlayerGame game;
    private transient Snake snake2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        this.game = (MultiPlayerGame) super.game;
        snake2 = mock(Snake.class);
        game.player2 = snake2;
    }

    @Override
    public void testConstructor() {
        super.testConstructor();

        assertEquals(snake2, game.getPlayer2());
        assertEquals(0, game.getScore2());
    }

    @Test
    public void testCheckSnakeCollision2CollidesWith1() {
        Square square = new Square(0, 0, 0, 0);
        doReturn(new ArrayList<>(Arrays.asList(square))).when(snake).getBody();
        doReturn(square).when(snake2).getHead();

        game.checkSnakeCollision();

        assertEquals(true, game.gameIsOver);
        verify(game, times(1)).gameOver(snake2);
        verify(launcher, times(1)).setScreen(any(MultiPlayerDeathScreen.class));
    }

    @Test
    public void testCheckSnakeCollision1CollidesWith2() {
        Square square = new Square(0, 0, 0, 0);
        Square square2 = new Square(1, 1, 1, 1);

        doReturn(new ArrayList<>(Arrays.asList(square))).when(snake).getBody();
        doReturn(square2).when(snake2).getHead();

        doReturn(new ArrayList<>(Arrays.asList(square2))).when(snake2).getBody();
        doReturn(square2).when(snake).getHead();

        game.checkSnakeCollision();

        assertEquals(true, game.gameIsOver);
        verify(game, times(1)).gameOver(snake);
        verify(launcher, times(1)).setScreen(any(MultiPlayerDeathScreen.class));
    }

    @Test
    public void testCheckSnakeCollisionNoCollision() {
        Square square = new Square(0, 0, 0, 0);
        Square square2 = new Square(1, 1, 1, 1);

        doReturn(new ArrayList<>(Arrays.asList(square))).when(snake).getBody();
        doReturn(square2).when(snake2).getHead();

        doReturn(new ArrayList<>(Arrays.asList(square2))).when(snake2).getBody();
        doReturn(square).when(snake).getHead();

        game.checkSnakeCollision();

        assertEquals(false, game.gameIsOver);
        verify(game, times(0)).gameOver(snake);
        verify(launcher, times(0)).setScreen(any(MultiPlayerDeathScreen.class));
    }

    @Test
    public void testGameOver() {
        assertEquals(false, game.gameIsOver);

        game.gameOver(snake);

        assertEquals(true, game.gameIsOver);
        verify(launcher, times(1)).setScreen(any(MultiPlayerDeathScreen.class));
    }

    @Test
    public void testGetPlayer2Head() {
        game.getPlayer2Head();

        verify(snake2, times(1)).getHead();
    }

    @Test
    public void testGetPlayer2Body() {
        game.getPlayer2Body();

        verify(snake2, times(1)).getBody();
    }

    @Test
    public void testGetPlayer2() {
        assertEquals(snake2, game.getPlayer2());
    }

    @Test
    public void testGetScore2() {
        assertEquals(0, game.getScore2());
    }

    @Test
    public void testCreateSnack() {
        game.createSnack();

        verify(builder, times(1)).createSnack();
    }

    @Test
    public void testForbiddenSquares() {
        game.getForbiddenSquares();

        verify(snake, times(1)).getBody();
        verify(snake2, times(1)).getBody();
    }
}
