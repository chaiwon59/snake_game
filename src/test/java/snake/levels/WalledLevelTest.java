package snake.levels;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import snake.gui.DeathScreen;
import snake.gui.Game;

public class WalledLevelTest extends LevelTest {

    public Level makeLevel(Game game, int stepSize) {
        return new WalledLevel(game, stepSize);
    }

    @Test
    public void testInvalidOutOfBoundsUp() {
        getLevel().createSnack((int) getWidth(), (int) getHeight());

        getLevel().move(getWidth() - getStepsize(), getHeight());
        verify(getGame(), new Times(1)).setScreen(any(DeathScreen.class));
    }

    @Test
    public void testInvalidOutOfBoundsRight() {
        getLevel().createSnack((int) getWidth(), (int) getHeight());

        getLevel().move(getWidth(), getHeight() - getStepsize());
        verify(getGame(), new Times(1)).setScreen(any(DeathScreen.class));
    }

    @Test
    public void testInvalidOutOfBoundsLeft() {
        getLevel().createSnack((int) getWidth(), (int) getHeight());

        getLevel().move(0 - getStepsize(), 0);
        verify(getGame(), new Times(1)).setScreen(any(DeathScreen.class));
    }

    @Test
    public void testInvalidOutOfBoundsDown() {
        getLevel().createSnack((int) getWidth(), (int) getHeight());

        getLevel().move(0, 0 - getStepsize());
        verify(getGame(), new Times(1)).setScreen(any(DeathScreen.class));
    }

}
