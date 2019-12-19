package snake.games.levels;

import snake.games.Game;
import snake.gui.LauncherClass;

public class NoWallsLevel extends Level {
    /**
     * Constructs the board.
     * @param stepSize determines how many squares
     *                 there are (the width and height should be divisible by this number)
     * @param launcherClass Current game
     */
    public NoWallsLevel(Game game, LauncherClass launcherClass, int stepSize) {
        super(game, launcherClass, stepSize);

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
        if (newY > getLauncherClass().getHeight() - getStepSize()) {
            move(newX, 0f, getCurrentSnake());
            return false;
        } else if (newX > getLauncherClass().getWidth() - getStepSize()) {
            move(0f, newY, getCurrentSnake());
            return false;
        } else if (newX < 0) {
            move(getLauncherClass().getWidth() - getStepSize(), newY, getCurrentSnake());
            return false;
        } else if (newY < 0) {
            move(newX, getLauncherClass().getHeight() - getStepSize(), getCurrentSnake());
            return false;
        }
        return true;
    }
}
