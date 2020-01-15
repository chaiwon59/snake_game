package snake.games.powerups;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.Snake;
import snake.games.builders.PowerUpBuilder;
import snake.squares.Square;

public class SpeedPowerUpTest extends PowerUpTest {
    transient SpeedPowerUp powerUp;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        this.powerUp = spy((SpeedPowerUp) super.powerUp);
    }

    @Override
    public PowerUp createPowerUp(PowerUpBuilder builder, Square rendered, Square actual) {
        return new SpeedPowerUp(builder, rendered, actual);
    }

    @Test
    public void testApply() {
        final int numberOfMoves = 1;
        Snake snake = mock(Snake.class);
        doReturn(numberOfMoves).when(snake).getNumberOfMoves();

        powerUp.apply(snake);

        verify(snake, times(1)).setNumberOfMoves(2 * numberOfMoves);
        verify(powerUp, times(1)).setActive();
    }

    @Test
    public void testUndo() {
        final int numberOfMoves = 2;
        Snake snake = mock(Snake.class);
        doReturn(numberOfMoves).when(snake).getNumberOfMoves();

        powerUp.undo(snake);

        verify(snake, times(1)).setNumberOfMoves(numberOfMoves / 2);
    }
}
