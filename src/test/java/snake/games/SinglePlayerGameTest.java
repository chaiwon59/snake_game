package snake.games;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.Dao;
import snake.gui.LauncherClass;
import snake.gui.deathscreens.SinglePlayerDeathScreen;

public class SinglePlayerGameTest extends GameTest {
    transient Dao dao;
    private transient SinglePlayerGame game;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        this.game = (SinglePlayerGame) super.game;
    }

    @Override
    public Game makeGame(LauncherClass launcher, int stepsize) {
        SinglePlayerGame game = new SinglePlayerGame(launcher, stepsize);

        this.dao = mock(Dao.class);
        game.setDao(dao);

        return game;
    }

    @Override
    @Test
    public void testGameOver() {
        super.testGameOver();
        verify(dao, times(1)).updateScore(isNull(), any(Integer.class));
        verify(launcher, times(1)).setScreen(any(SinglePlayerDeathScreen.class));
    }

    @Test
    public void updateScore() {
        assertEquals(0, game.getScore1());

        game.updateScore(snake);

        verify(snake, times(1)).increaseScore();
    }

    @Test
    public void testCreateSnack() {
        builder.createSnack();
        verify(builder, times(1)).createSnack();
    }

    @Test
    public void testForbiddenSquares() {
        game.getForbiddenSquares();

        verify(snake, times(1)).getBody();
    }
}
