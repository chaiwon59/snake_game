package snake.games.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import snake.games.Game;
import snake.games.powerups.PowerUp;
import snake.games.powerups.PowerUps;
import snake.games.powerups.ScoreIncreasePowerUp;
import snake.games.powerups.SpeedPowerUp;
import snake.gui.LauncherClass;
import snake.squares.Square;

public class PowerUpBuilder extends Builder {
    static final transient int low = 10;
    static final transient int high = 20;
    transient float nextPowerUpTime = getRandomTime();

    transient Square powerUpRendered;
    transient Square powerUpSquare;
    transient PowerUp powerUp;
    transient List<PowerUp> activePowerUps;

    transient boolean created;

    /**
     * Creates instance of the PowerUpBuilder class.
     *
     * @param launcherClass instance of the launcher class
     * @param stepSize      size of the steps used when creating squares
     * @param squareSize    size of the squares
     */
    public PowerUpBuilder(Game game, LauncherClass launcherClass, int stepSize, int squareSize) {
        super(game, launcherClass, stepSize, squareSize);
        this.activePowerUps = new ArrayList<>();
        this.created = false;
    }

    /**
     * Creates a new powerup with randomized location.
     */
    public void createPowerUp() {
        created = true;

        Pair newPowerUp = create();

        this.powerUpRendered = newPowerUp.rendered;
        this.powerUpSquare = newPowerUp.actual;

        PowerUps current = PowerUps.values()[new Random().nextInt(PowerUps.values().length)];
        switch (current) {
            case SPEEDUP:
                powerUp = new SpeedPowerUp(this, powerUpRendered, powerUpSquare);
                break;
            default:
                powerUp = new ScoreIncreasePowerUp(this, powerUpRendered, powerUpSquare);
                break;
        }
    }

    /**
     * Generates random number between low (inclusive) and high(exclusive).
     * @return int representing random number
     */
    public float getRandomTime() {
        return new Random().nextInt(high - low) + low;
    }

    /**
     * Decreases the remaining active time of all the power ups
     * and checks whether a new power up needs to be created.
     * @param delta time passed
     */
    public void decreaseTime(float delta) {
        activePowerUps.removeIf(current -> current.decreaseTime(delta));

        nextPowerUpTime -= delta;
        if (nextPowerUpTime <= 0 && !created) {
            createPowerUp();
        }
    }

    /**
     * Resets the timer for when a new power up is created.
     */
    public void resetNextPowerUpTime() {
        nextPowerUpTime = getRandomTime();
        created = false;
    }

    public Square getPowerUpSquare() {
        return powerUpSquare;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public void addActivePowerUp(PowerUp powerUp) {
        activePowerUps.add(powerUp);
    }
}
