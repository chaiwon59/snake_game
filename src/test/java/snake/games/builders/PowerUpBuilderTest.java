package snake.games.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.games.Game;
import snake.games.powerups.PowerUp;
import snake.gui.LauncherClass;

public class PowerUpBuilderTest extends BuilderTest {
    transient PowerUpBuilder builder;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        this.builder = (PowerUpBuilder) super.builder;
    }

    @Override
    public Builder createBuilder(Game game, LauncherClass launcher, int stepSize, int squareSize) {
        return new PowerUpBuilder(game, launcher, stepSize, squareSize);
    }

    @Test
    public void testCreatePowerUp() {
        assertFalse(builder.created);
        assertNull(builder.powerUp);
        assertNull(builder.powerUpRendered);
        assertNull(builder.powerUpSquare);

        builder.createPowerUp();

        assertTrue(builder.created);
        assertNotNull(builder.powerUp);
        assertNotNull(builder.powerUpRendered);
        assertNotNull(builder.powerUpSquare);
    }

    @Test
    public void testGetPowerUpSquare() {
        assertNull(builder.getPowerUpSquare());

        builder.createPowerUp();

        assertNotNull(builder.getPowerUpSquare());
    }

    @Test
    public void testGetPowerUp() {
        assertNull(builder.getPowerUp());

        builder.createPowerUp();

        assertNotNull(builder.getPowerUp());
    }

    @Test
    public void testGetRandomTime() {
        float randomTime = builder.getRandomTime();
        assertTrue(randomTime >= builder.low && randomTime < builder.high);
    }

    @Test
    public void testDecreaseTimeBiggerThanZeroNotCreated() {
        final float prevPowerUpTime = builder.nextPowerUpTime;
        float delta = builder.nextPowerUpTime / 4;
        PowerUp testPower = mock(PowerUp.class);
        builder.activePowerUps.add(testPower);

        builder.decreaseTime(delta);

        verify(testPower, times(1)).decreaseTime(delta);
        verify(builder, times(0)).createPowerUp();
        assertEquals(prevPowerUpTime - delta, builder.nextPowerUpTime);
        assertFalse(builder.created);
        assertTrue(builder.nextPowerUpTime > 0);
    }

    @Test
    public void testDecreaseTimeLowerThanZeroNotCreated() {
        float delta = builder.nextPowerUpTime;
        PowerUp testPower = mock(PowerUp.class);
        builder.activePowerUps.add(testPower);

        builder.decreaseTime(delta);

        verify(testPower, times(1)).decreaseTime(delta);
        verify(builder, times(1)).createPowerUp();
        assertEquals(0, builder.nextPowerUpTime);
        assertTrue(builder.created);
    }

    @Test
    public void resetNextPowerUpTime() {
        builder.created = true;
        builder.nextPowerUpTime = 0;

        builder.resetNextPowerUpTime();

        assertFalse(builder.created);
        assertTrue(builder.nextPowerUpTime >= builder.low
                && builder.nextPowerUpTime < builder.high);
    }

    @Test
    public void testAddActivePowerUp() {
        PowerUp power = mock(PowerUp.class);
        assertEquals(0, builder.activePowerUps.size());

        builder.addActivePowerUp(power);

        assertEquals(1, builder.activePowerUps.size());
        assertTrue(builder.activePowerUps.contains(power));
    }
}
