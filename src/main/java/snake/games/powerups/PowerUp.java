package snake.games.powerups;

import snake.Player;
import snake.Snake;
import snake.games.builders.PowerUpBuilder;
import snake.squares.Square;

public abstract class PowerUp {
    transient PowerUpBuilder builder;
    transient float timeRemaining;
    transient Square rendered;
    transient Square actual;
    transient boolean active;
    transient Player snake;

    /**
     * Constructor of the power up class.
     *
     * @param rendered square to be rendered.
     * @param actual   actual location of the square
     */
    public PowerUp(PowerUpBuilder builder, Square rendered, Square actual) {
        this.builder = builder;
        this.rendered = rendered;
        this.actual = actual;
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive() {
        this.active = !active;
    }


    /**
     * Applies the effect of the power up.
     */
    public void apply(Player snake) {
        this.snake = snake;
        setActive();
        builder.addActivePowerUp(this);
    }

    /**
     * Undos the effect of the power up.
     */
    public abstract void undo(Player snake);

    /**
     * Decreases the remaining time of the power up and executes the undo when necessary.
     *
     * @param delta time passed.
     */
    public boolean decreaseTime(float delta) {
        timeRemaining -= delta;
        if (timeRemaining <= 0) {
            undo(snake);
            return true;
        }
        return false;
    }
}
