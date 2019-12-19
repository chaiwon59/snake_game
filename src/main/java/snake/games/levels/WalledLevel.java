package snake.games.levels;

import snake.games.Game;
import snake.gui.LauncherClass;

public class WalledLevel extends Level {

    /**
     * Constructs the board.
     * @param stepSize determines how many squares
     *                 there are (the width and height should be divisible by this number)
     * @param launcherClass Current game
     */
    public WalledLevel(Game game, LauncherClass launcherClass, int stepSize) {
        super(game, launcherClass,stepSize);
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
        if (newY > getLauncherClass().getHeight() - getStepSize()
                || newX > getLauncherClass().getWidth() - getStepSize()
                || newX < 0
                || newY < 0) {
            setDied();
            return false;
        }
        return true;
    }
}