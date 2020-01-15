package snake.games.levels;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import snake.games.Game;
import snake.gui.LauncherClass;

public class WalledLevelTest extends LevelTest {

    public Level makeLevel(Game game, LauncherClass launcherClass, int stepSize) {
        return new WalledLevel(game, launcherClass, stepSize);
    }

    @Test
    public void testInvalidOutOfBoundsUp() {
        builder.createSnack((int) getWidth(), (int) getHeight(), snake.getBody());

        getLevel().move(getWidth() - getStepsize(), getHeight(), snake);
        verify(game, times(1)).gameOver(snake);
    }

    @Test
    public void testInvalidOutOfBoundsRight() {
        builder.createSnack((int) getWidth(), (int) getHeight(), snake.getBody());

        getLevel().move(getWidth(), getHeight() - getStepsize(), snake);
        verify(game, times(1)).gameOver(snake);
    }

    @Test
    public void testInvalidOutOfBoundsLeft() {
        builder.createSnack((int) getWidth(), (int) getHeight(), snake.getBody());

        getLevel().move(0 - getStepsize(), 0, snake);
        verify(game, times(1)).gameOver(snake);
    }

    @Test
    public void testInvalidOutOfBoundsDown() {
        builder.createSnack((int) getWidth(), (int) getHeight(), snake.getBody());

        getLevel().move(0, 0 - getStepsize(), snake);
        verify(game, times(1)).gameOver(snake);
    }

}
