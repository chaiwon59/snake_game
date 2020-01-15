package snake.games.powerups;

import snake.Snake;
import snake.games.builders.PowerUpBuilder;
import snake.squares.Square;

public class SpeedPowerUp extends PowerUp {
    public SpeedPowerUp(PowerUpBuilder builder, Square rendered, Square actual) {
        super(builder, rendered, actual);
        this.timeRemaining = 10L;
    }

    @Override
    public void apply(Snake snake) {
        super.apply(snake);
        snake.setNumberOfMoves(snake.getNumberOfMoves() * 2);
    }

    @Override
    public void undo(Snake snake) {
        snake.setNumberOfMoves(snake.getNumberOfMoves() / 2);
    }
}
