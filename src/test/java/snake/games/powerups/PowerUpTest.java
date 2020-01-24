package snake.games.powerups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.Player;
import snake.Snake;
import snake.games.builders.PowerUpBuilder;
import snake.squares.Square;

public abstract class PowerUpTest {
    transient PowerUp powerUp;
    transient Square rendered;
    transient Square actual;
    transient PowerUpBuilder builder;
    transient Player player;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    public void setUp() {
        this.rendered = mock(Square.class);
        this.actual = mock(Square.class);
        this.builder = mock(PowerUpBuilder.class);
        this.player = mock(Player.class);

        this.powerUp = spy(createPowerUp(builder, rendered, actual));
    }

    public abstract PowerUp createPowerUp(PowerUpBuilder builder, Square rendered, Square actual);

    @Test
    public void testConstructor() {
        assertEquals(rendered, powerUp.rendered);
        assertEquals(actual, powerUp.actual);
        assertFalse(powerUp.active);
    }

    @Test
    public void testIsActive() {
        assertEquals(false, powerUp.isActive());

        powerUp.active = true;

        assertEquals(true, powerUp.isActive());
    }

    @Test
    public void testSetActive() {
        assertEquals(false, powerUp.isActive());

        powerUp.setActive();

        assertEquals(true, powerUp.isActive());

        powerUp.setActive();

        assertEquals(false, powerUp.isActive());
    }

    @Test
    public void testApply() {
        assertNull(powerUp.snake);

        powerUp.apply(player);

        assertEquals(player, powerUp.snake);
        verify(powerUp, times(1)).setActive();
        verify(builder, times(1)).addActivePowerUp(powerUp);
    }

    @Test
    public void testDecreaseTimeBiggerThanZero() {
        float delta = powerUp.timeRemaining / 4;

        assertFalse(powerUp.decreaseTime(delta));
        assertTrue(powerUp.timeRemaining > 0);
        verify(powerUp, times(0)).undo(any(Player.class));
    }

    @Test
    public void testDecreaseTimeLowerThanZero() {
        float delta = powerUp.timeRemaining + 1;
        powerUp.snake = player;

        assertTrue(powerUp.decreaseTime(delta));
        assertTrue(powerUp.timeRemaining <= 0);
        verify(powerUp, times(1)).undo(player);
    }
}
