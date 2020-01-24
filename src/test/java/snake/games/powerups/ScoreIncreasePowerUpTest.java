package snake.games.powerups;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.Snake;
import snake.games.builders.PowerUpBuilder;
import snake.squares.Square;

public class ScoreIncreasePowerUpTest extends PowerUpTest {
    transient ScoreIncreasePowerUp powerUp;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        this.powerUp = spy((ScoreIncreasePowerUp) super.powerUp);
    }

    @Override
    public PowerUp createPowerUp(PowerUpBuilder builder, Square rendered, Square actual) {
        return new ScoreIncreasePowerUp(builder, rendered, actual);
    }

    @Test
    public void testApply() {
        final int scoreIncrease = 10;
        doReturn(scoreIncrease).when(player).getScoreIncrease();

        powerUp.apply(player);

        verify(player, times(1)).setScoreIncrease(2 * scoreIncrease);
        verify(powerUp, times(1)).setActive();
    }

    @Test
    public void testUndo() {
        final int scoreIncrease = 20;
        doReturn(scoreIncrease).when(player).getScoreIncrease();

        powerUp.undo(player);

        verify(player, times(1)).setScoreIncrease(scoreIncrease / 2);
    }
}
