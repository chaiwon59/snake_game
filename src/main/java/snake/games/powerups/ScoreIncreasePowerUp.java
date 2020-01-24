package snake.games.powerups;

import snake.Player;
import snake.Snake;
import snake.games.builders.PowerUpBuilder;
import snake.squares.Square;

public class ScoreIncreasePowerUp extends PowerUp {

    public ScoreIncreasePowerUp(PowerUpBuilder builder, Square rendered, Square actual) {
        super(builder, rendered, actual);
        this.timeRemaining = 20L;
    }

    /**
     * Applies the given effect to the snake and schedules the undo.
     *
     * @param snake player on which the action will take effect.
     */
    @Override
    public void apply(Player snake) {
        super.apply(snake);

        snake.setScoreIncrease(2 * snake.getScoreIncrease());
    }

    /**
     * Undoes the given effect.
     *
     * @param snake player on which the action will take effect.
     */
    public void undo(Player snake) {
        snake.setScoreIncrease(snake.getScoreIncrease() / 2);
    }
}
