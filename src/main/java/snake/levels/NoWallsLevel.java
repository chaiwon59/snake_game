package snake.levels;

import snake.Snake;
import snake.gui.Game;

public class NoWallsLevel extends Level {
    /**
     * Constructs the board.
     * @param stepSize determines how many squares
     *                 there are (the width and height should be divisible by this number)
     * @param game Current game
     */
    public NoWallsLevel(Game game, int stepSize) {
        super(game, stepSize);
    }

    /**
     * Checks whether the move is valid.
     *
     * @param newX new x value of the head of the snake.
     * @param newY new y value of the head of the snake.
     * @return true if the move is valid, false if it is not.
     */
    @Override
    public boolean checkValidity(float newX, float newY) {
        if (newY > getGame().getHeight() - getStepSize()) {
            move(newX, 0f);
            return false;
        } else if (newX > getGame().getWidth() - getStepSize()) {
            move(0f, newY);
            return false;
        } else if (newX < 0) {
            move(getGame().getWidth() - getStepSize(), newY);
            return false;
        } else if (newY < 0) {
            move(newX, getGame().getHeight() - getStepSize());
            return false;
        }
        return true;
    }
}